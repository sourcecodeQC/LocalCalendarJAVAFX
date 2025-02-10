package com.example.localcalendarjavafx;

import java.util.List;

public class PriorityCollisionHandler {

    public static void checkForCollisions(List<Event> events) {
        for (int i = 0; i < events.size(); i++) {
            Event event1 = events.get(i);
            for (int j = i + 1; j < events.size(); j++) {
                Event event2 = events.get(j);
                // Check for time collisions
                if (event1.getDate().equals(event2.getDate())) {
                    if (isOverlapping(event1, event2)) {
                        System.out.println("Collision detected between: " + event1.getTitle() + " and " + event2.getTitle());
                    }
                }
            }
        }
    }

    private static boolean isOverlapping(Event event1, Event event2) {
        // Check if event1 overlaps with event2
        return (event1.getStartHHMM() < event2.getEndHHMM() && event1.getEndHHMM() > event2.getStartHHMM());
    }
}