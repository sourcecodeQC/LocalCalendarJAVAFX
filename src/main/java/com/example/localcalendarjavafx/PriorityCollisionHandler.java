package com.example.localcalendarjavafx;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
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
                // Check priority
                String message = newEvent.getPriority() < existingEvent.getPriority() ?
                        "New event has lower priority than an existing event!" :
                        "New event has higher priority than an existing event!";

                // Display overlapping events
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
                        moveEvent(existingEvent, newEvent.getEndTime());
                        eventListView.getItems().remove(existingEvent); // Remove the old event
                        eventListView.getItems().add(existingEvent); // Add the updated event
                    } else if (result.get() == amendNew) {
                        moveEvent(newEvent, existingEvent.getEndTime());
                        eventListView.getItems().remove(newEvent); // Remove the old event
                        eventListView.getItems().add(newEvent); // Add the updated event
                    } else if (result.get() == abort) {
                        System.out.println("Aborting the addition of the new event.");
                        return; // Exit without adding the new event
                    } else if (result.get() == override) {
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

    private static void moveEvent(Event event, int newStartTime) {
        int newEndTime = newStartTime + (event.getEndTime() - event.getStartTime());
        event = new Event(event.getTitle(), event.getDate(), newStartTime, newEndTime, event.getPriority());
        System.out.println("Event moved to new time: " + event);
    }

    public static void printEventDetails(Event event) {
        System.out.println("Event: " + event.getTitle());
        System.out.println("Date: " + event.getDate());
        System.out.println("Start Time: " + event.getStartHHMM());
        System.out.println("End Time: " + event.getEndHHMM());
        System.out.println("Priority: " + event.getPriority());
    }
}