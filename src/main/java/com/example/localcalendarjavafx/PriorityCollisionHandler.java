// .equalsIgnoreCase learned from https://www.w3schools.com/java/ref_string_equalsignorecase.asp
// basing the logic on the switching cases suggested by the AI; however, the program was written individually

import java.util.List;
import java.util.Scanner;

public class PriorityCollisionHandler {
    public static final Scanner scx = new Scanner(System.in); // Declare scanner globally

    public static boolean checkCollision(List<Event> events, Event newEvent) {
        for (Event existingEvent : events) {
            if (existingEvent.getDate().equals(newEvent.getDate()) && isOverlapping(existingEvent, newEvent)) {
                // Collision detected
                return handleCollision(existingEvent, newEvent);
            }
        }
        return false; // No collision detected, event can be added
    }

    private static boolean isOverlapping(Event existingEvent, Event newEvent) {
        return newEvent.getStartTime() < existingEvent.getEndTime() &&
                newEvent.getEndTime() > existingEvent.getStartTime();
    }

    private static boolean handleCollision(Event existingEvent, Event newEvent) {
        if (existingEvent.getPriority() < newEvent.getPriority()) { // Existing event has higher priority
            System.out.println("Conflicting event: " + existingEvent);
            System.out.println("New event has lower priority! Choose an action:");
            System.out.println("1. Amend (move) the existing event");
            System.out.println("2. Aborting the addition of a new event");
            System.out.println("3. Override and add both events");
            String choice = scx.nextLine();

            switch (choice) {
                case "1":
                    moveEvent(existingEvent, newEvent);
                    return false; // Event added after amendment

                case "2":
                    System.out.println("New Event Aborted");
                    return true; // Collision detected, event not added

                case "3":
                    System.out.println("Both events added");
                    return false; // Both events can be added

                default:
                    System.out.println("Invalid choice. New Event Aborted.");
                    return true; // Collision detected, event not added
            }

        }

        else if (existingEvent.getPriority() > newEvent.getPriority()) { // New event has higher priority
            System.out.println("Conflicting event: " + existingEvent);
            System.out.println("New event has higher priority! Choose an action:");
            System.out.println("1. Amend (move) the new event");
            System.out.println("2. Aborting the addition of a new event");
            System.out.println("3. Override and add both events");
            String choice = scx.nextLine();

            switch (choice) {
                case "1":
                    moveEvent(newEvent, existingEvent);
                    return false; // Event added after amendment

                case "2":
                    System.out.println("New Event Aborted");
                    return true; // Collision detected, event not added

                case "3":
                    System.out.println("Both events added");
                    return false; // Both events can be added

                default:
                    System.out.println("Invalid choice. New Event Aborted.");
                    return true; // Collision detected, event not added
            }

        }

        else { // Both events have the same priority
            System.out.println("Conflicting event: " + existingEvent);
            System.out.println("Both events have the same priority! Choose an action:");
            System.out.println("1. Amend (move) the existing event");
            System.out.println("2. Amend (move) the new event");
            System.out.println("3. Aborting the addition of a new event");
            System.out.println("4. Override and add both events");
            String choice = scx.nextLine();

            switch (choice) {
                case "1":
                    moveEvent(existingEvent, newEvent);
                    return false; // Event added after amendment

                case "2":
                    moveEvent(newEvent, existingEvent);
                    return false; // Event added after amendment

                case "3":
                    System.out.println("New Event Aborted");
                    return true; // Collision detected, event not added

                case "4":
                    System.out.println("Both events added");
                    return false; // Both events can be added

                default:

                    System.out.println("Invalid choice. New Event Aborted.");
                    return true; // Collision detected, event not added

            }
        }
    }

    private static void moveEvent(Event existingEvent, Event newEvent) {
        // Move the existing event to create a gap of 15 minutes
        int newStartTime = newEvent.getEndTime() + 15; // 15 minutes after the new event
        existingEvent.setStartTime(newStartTime); // Update the start time using setter
        existingEvent.setEndTime(newStartTime + (existingEvent.getEndTime() - existingEvent.getStartTime())); // Adjust end time using setter
        System.out.println("Existing event moved to: " + existingEvent);
    }
}