package com.example.localcalendarjavafx;

public class PriorityCollisionHandler {

    /**
     * Checks if two events collide based on their start and end times.
     *
     * @param event1 The first event to check.
     * @param event2 The second event to check.
     * @return true if the events collide, false otherwise.
     */
    public static boolean checkCollision(Event event1, Event event2) {
        // Check if the events collide based on their start and end times
        return (event1.getStartTime() < event2.getEndTime() && event1.getEndTime() > event2.getStartTime());
    }

    /**
     * Prints the details of an event.
     *
     * @param event The event whose details are to be printed.
     */
    public static void printEventDetails(Event event) {
        System.out.println("Event: " + event.getTitle());
        System.out.println("Date: " + event.getDate());
        System.out.println("Start Time: " + event.getStartHHMM());
        System.out.println("End Time: " + event.getEndHHMM());
        System.out.println("Priority: " + event.getPriority());
    }
}