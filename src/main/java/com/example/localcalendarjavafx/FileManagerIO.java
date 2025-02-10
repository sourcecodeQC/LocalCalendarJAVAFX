package com.example.localcalendarjavafx;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManagerIO {
    private static final String DIRECTORY_NAME = "SAVE_DATA"; // Directory to save the CSV file
    private static final String FILENAME = DIRECTORY_NAME + "/events.csv"; // Path to the CSV file

    public static void saveEvents(List<Event> events) {
        File directory = new File(DIRECTORY_NAME);
        if (!directory.exists()) {
            directory.mkdir(); // Create the directory
        }

        try (BufferedWriter outputIO = new BufferedWriter(new FileWriter(FILENAME))) {
            outputIO.write("Title,Date,StartTime,EndTime,Priority");
            outputIO.newLine();

            for (Event event : events) {
                outputIO.write(event.getTitle() + "," + event.getDate() + "," +
                        event.getStartTime() + "," + event.getEndTime() + "," +
                        event.getPriority());
                outputIO.newLine();
                System.out.println("Saving event: " + event); // Debug statement
            }
            System.out.println("All events saved to " + FILENAME);
            showConfirmationPopup(); // Show confirmation popup after saving
        } catch (IOException e) {
            System.err.println("Error saving events: " + e.getMessage());
        }
    }

    public static List<Event> loadEvents() {
        List<Event> events = new ArrayList<>();
        File file = new File(FILENAME);
        if (!file.exists()) {
            System.out.println("No saved events found. Returning an empty list.");
            return events; // Return empty list if file does not exist
        }

        try (BufferedReader fetchIO = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            fetchIO.readLine(); // Skip header

            while ((line = fetchIO.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
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
        return events;
    }

    private static void showConfirmationPopup() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Save Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("All events have been saved successfully!");
        alert.showAndWait();
    }
}