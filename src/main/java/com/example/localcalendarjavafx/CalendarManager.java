package com.example.localcalendarjavafx;

import java.util.ArrayList;
import java.util.List;

public class CalendarManager {
    private static List<Event> events = new ArrayList<>();

    public static void addEvent(Event event) {
        events.add(event);
        System.out.println("Event added: " + event);
    }

    public static List<Event> getEvents() {
        return new ArrayList<>(events); // Return a copy of the list
    }

    public static void deleteEvent(String title) {
        events.removeIf(event -> event.getTitle().equalsIgnoreCase(title));
        System.out.println("Event deleted: " + title);
    }

    public static void deleteAllEvents() {
        events.clear();
        System.out.println("All events deleted.");
    }

    public static void saveEvents() {
        // Implement your saving logic here (e.g., save to a file)
        System.out.println("Events saved.");
    }

    public static void loadEvents() {
        // Implement your loading logic here (e.g., load from a file)
        System.out.println("Events loaded.");
    }


}