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
                        // Move existing event to start 30 minutes after the new event ends
                        moveExistingEvent(existingEvent, newEvent.getEndTime() + 30, eventListView.getItems());
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
                        CalendarManager.addEvent(newEvent);
                        FileManagerIO.saveEvents(CalendarManager.getEvents());
                        System.out.println("Both events added successfully.");
                    }
                }
                return; // Exit after handling the first conflict
            }
        }

        // If no conflicts, add the event
        CalendarManager.addEvent(newEvent);
        FileManagerIO.saveEvents(CalendarManager.getEvents());
        System.out.println("Event added successfully.");
    }

    private static void moveExistingEvent(Event existingEvent, int newStartTime, List<Event> eventList) {
        int duration = existingEvent.getEndTime() - existingEvent.getStartTime();
        int newEndTime = newStartTime + duration;

        Event updatedEvent = new Event(existingEvent.getTitle(), existingEvent.getDate(), newStartTime, newEndTime, existingEvent.getPriority());

        // Update the event in the list
        int index = eventList.indexOf(existingEvent);
        if (index != -1) {
            eventList.set(index, updatedEvent); // Replace the old event with the updated one
        }
    }
}