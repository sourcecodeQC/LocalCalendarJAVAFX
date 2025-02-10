import java.util.ArrayList; //suggested by ai; learned from external source
import java.util.List; //suggested by ai; learned from external source


public class CalendarManager {
    private List<Event> events;

    public CalendarManager() {
        events = FileManagerIO.loadEvents(); // fetch
        if (events == null) {
            events = new ArrayList<>(); // Initialize if loading fails // Initialize the list of events - https://www.w3schools.com/java/java_arraylist.asp
        }
    }

    //public CalendarManager() {
        //events = new ArrayList<>(); // Initialize the list of events - https://www.w3schools.com/java/java_arraylist.asp
    //}

    public void addEvent(String title, String date, int startHHMM, int endHHMM, int priority) {
        // Convert HHMM to minutes from midnight // Suggestion of teacher; compute on minutes from midnight, allow input as hhmm
        int startTime = HourToMin.convertToMin(startHHMM);
        int endTime = HourToMin.convertToMin(endHHMM);

        // Check for collision with existing events //Collision test
        Event newEvent = new Event(title, date, startTime, endTime, priority);
        if (PriorityCollisionHandler.checkCollision(events, newEvent)) {
            System.out.println("Event may not be added due to a collision with an existing event.");
            return;
        }

        // Add the new event to the list
        events.add(newEvent);
        System.out.println("Event added: " + title + " ; from: " + startHHMM + " ; to: " + endHHMM + " ; with priority: " + priority);
    }

    public void listEvents() {
        if (events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            System.out.println("Events:");
            for (Event event : events) { //for (int i = 0; i < events.size(); i++) https://www.w3schools.com/java/java_foreach_loop.asp
                System.out.println(event);
            }
        }
    }

    public void saveEvents() {
        FileManagerIO.saveEvents(events);
    }

    public void deleteEvent(String title) {
        boolean removed = events.removeIf(event -> event.getTitle().equalsIgnoreCase(title)); // lambda expr (->) suggested by ai; learned from https://www.w3schools.com/java/java_lambda.asp
        if (removed) {
            System.out.println("Event deleted: " + title);
        } else {
            System.out.println("Event not found: " + title);
        }
    }

    public void deleteAllEvents() {
        events.clear(); // Clear the list
        System.out.println("All events deleted.");
        FileManagerIO.saveEvents(events); // save empty list
    }
}