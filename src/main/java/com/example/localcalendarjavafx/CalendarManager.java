package com.example.localcalendarjavafx;

import java.util.ArrayList;
import java.util.Arrays;
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

    //method to sort events ascending/decesnding based on priority

    public static Event[] sortGUIPriority(boolean ascending){

        Event[] sortedEvents = events.toArray(new Event[0]);

        // Selection Sort algorithm
        for (int i = 0; i < sortedEvents.length - 1; i++) {
            int index = i;

            for (int j = i + 1; j < sortedEvents.length; j++) {
                if ((ascending && sortedEvents[j].getPriority() < sortedEvents[index].getPriority()) ||
                        (!ascending && sortedEvents[j].getPriority() > sortedEvents[index].getPriority())) {
                    index = j;
                }
            }


            if (index != i) {
                Event temp = sortedEvents[i];
                sortedEvents[i] = sortedEvents[index];
                sortedEvents[index] = temp;
            }
        }
        return sortedEvents;
    }


    public void displayEvents(Event[] eventsToDisplay) {
        for (Event event : eventsToDisplay) {
            System.out.println(event);
        }



    }
}
