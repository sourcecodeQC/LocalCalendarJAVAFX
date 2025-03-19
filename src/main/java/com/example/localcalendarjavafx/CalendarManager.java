package com.example.localcalendarjavafx;

import java.util.ArrayList;
import java.util.List;

public class CalendarManager {
    private static List<Event> events = new ArrayList<>();

    public static void addEvent(Event event) {
        events.add(event); // Add event to the list
        System.out.println("Event added: " + event); // Debug statement
        saveEvents(); // Save events after adding a new event
    }

    public static List<Event> getEvents() {
        return events; // Retrieve the list of events
    }

    public static void loadEvents() {
        events = FileManagerIO.loadEvents(); // Load events from file
    }

    public static void saveEvents() {
        System.out.println("Saving events: " + events); // Debug statement
        FileManagerIO.saveEvents(events); // Save events using the original method
        System.out.println("All events saved successfully."); // Debug statement
    }
}
