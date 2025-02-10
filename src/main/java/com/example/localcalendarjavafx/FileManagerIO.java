package com.example.localcalendarjavafx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManagerIO {
    private static final String DIRECTORY_NAME = "SAVE_DATA"; // Directory to save the CSV file
    private static final String FILENAME = DIRECTORY_NAME + "/events.csv"; // Path to the CSV file

    // Save events to a CSV file
    public static void saveEvents(List<Event> events) {
        // Create the SAVE_DATA directory if it doesn't exist
        File directory = new File(DIRECTORY_NAME);

        if (!directory.exists()) {
            directory.mkdir(); // Create the directory
        }

        try (BufferedWriter outputIO = new BufferedWriter(new FileWriter(FILENAME))) {
            // Write header
            outputIO.write("Title,Date,StartTime,EndTime,Priority");
            outputIO.newLine();

            // Write each event
            for (Event event : events) {
                outputIO.write(event.getTitle() + "," + event.getDate() + "," +
                        event.getStartTime() + "," + event.getEndTime() + "," +
                        event.getPriority());
                outputIO.newLine(); // Move to the next line for the next event
            }
            System.out.println("Events saved to " + FILENAME);
        } catch (IOException e) {
            System.err.println("Error saving events: " + e.getMessage());
        }
    }

    // Load events from a CSV file
    public static List<Event> loadEvents() {
        List<Event> events = new ArrayList<>();
        try (BufferedReader fetchIO = new BufferedReader(new FileReader(FILENAME))) {
            String line;

            // Skip header
            fetchIO.readLine();

            // Read each line and create Event objects
            while ((line = fetchIO.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 5) { // Ensure there are 5 parts
                    String title = parts[0];
                    String date = parts[1];
                    int startTime = Integer.parseInt(parts[2]);
                    int endTime = Integer.parseInt(parts[3]);
                    int priority = Integer.parseInt(parts[4]);
                    events.add(new Event(title, date, startTime, endTime, priority));
                }
            }

            System.out.println("Events loaded from " + FILENAME);
        } catch (IOException e) {
            System.err.println("Error loading events: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing event data: " + e.getMessage());
        }

        return events; // Return the list of events
    }
}