package com.example.localcalendarjavafx;

import java.util.ArrayList;
import java.util.List;

public class CalendarManager {
    private static List<Event> events = new ArrayList<>();

    public static void addEvent(Event event) {
        events.add(event); // Add event to the list
    }

    public static List<Event> getEvents() {
        return events; // Retrieve the list of events
    }

    public static void loadEvents() {
        events = FileManagerIO.loadEvents(); // Load events from file
    }

    public static void saveEvents() {
        FileManagerIO.saveEvents(events); // Save events using the original method
    }
}