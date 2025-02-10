package com.example.localcalendarjavafx;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.Optional;

public class PriorityCollisionHandler {

    public static boolean checkCollision(Event event1, Event event2) {
        return (event1.getStartTime() < event2.getEndTime() && event1.getEndTime() > event2.getStartTime());
    }

    public static void handleEventAddition(Event newEvent, List<Event> existingEvents, ListView<Event> eventListView) {
        for (Event existingEvent : existingEvents) {
            if (checkCollision(newEvent, existingEvent)) {
                String message = newEvent.getPriority() < existingEvent.getPriority() ?
                        "New event has lower priority than an existing event!" :
                        "New event has higher priority than an existing event!";

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Event Collision");
                alert.setHeaderText(message);
                alert.setContentText("Conflicting Event: " + existingEvent + "\nNew Event: " + newEvent +
                        "\nChoose an action:\n1. Amend (move) the existing event\n2. Amend (move) the new event\n3. Aborting the addition of a new event\n4. Override and add both events");

                ButtonType amendExisting = new ButtonType("Amend Existing Event");
                ButtonType amendNew = new ButtonType("Amend New Event");
                ButtonType abort = new ButtonType("Abort");
                ButtonType override = new ButtonType("Override");

                alert.getButtonTypes().setAll(amendExisting, amendNew, abort, override);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent()) {
                    if (result.get() == amendExisting) {
                        // Move existing event to end 30 minutes before the new event starts
                        int newEndTime = newEvent.getStartTime() - 30;
                        int duration = existingEvent.getEndTime() - existingEvent.getStartTime();
                        int newStartTime = newEndTime - duration;
                        Event updatedExistingEvent = new Event(existingEvent.getTitle(), existingEvent.getDate(), newStartTime, newEndTime, existingEvent.getPriority());
                        eventListView.getItems().remove(existingEvent);
                        eventListView.getItems().add(updatedExistingEvent);

                        // Adjust new event to maintain its original duration
                        int newEventStartTime = newEndTime + 30;
                        int newEventEndTime = newEventStartTime + (newEvent.getEndTime() - newEvent.getStartTime());
                        Event updatedNewEvent = new Event(newEvent.getTitle(), newEvent.getDate(), newEventStartTime, newEventEndTime, newEvent.getPriority());
                        eventListView.getItems().remove(newEvent);
                        eventListView.getItems().add(updatedNewEvent);
                    } else if (result.get() == amendNew) {
                        // Move new event to start 30 minutes after the existing event ends
                        int newStartTime = existingEvent.getEndTime() + 30;
                        int duration = newEvent.getEndTime() - newEvent.getStartTime();
                        int newEndTime = newStartTime + duration;
                        Event updatedEvent = new Event(newEvent.getTitle(), newEvent.getDate(), newStartTime, newEndTime, newEvent.getPriority());
                        eventListView.getItems().remove(newEvent);
                        eventListView.getItems().add(updatedEvent);
                    } else if (result.get() == abort) {
                        System.out.println("Aborting the addition of the new event.");
                        return; // Exit without adding the new event
                    } else if (result.get() == override) {
                        // Add the new event without any adjustments
                        eventListView.getItems().add(newEvent);
                    }
                    CalendarManager.saveEvents(); // Save events after any modification
                }
                return; // Exit after handling the first conflict
            }
        }

        // If no conflicts, add the event
        CalendarManager.addEvent(newEvent);
        CalendarManager.saveEvents(); // Save events after adding a new event
        System.out.println("Event added successfully.");
    }
}