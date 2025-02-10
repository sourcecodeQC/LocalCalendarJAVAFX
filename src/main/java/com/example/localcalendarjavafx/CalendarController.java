package com.example.localcalendarjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;

import java.util.List;
import java.util.Optional;

public class CalendarController {

    @FXML
    private TableView<Event> eventTable;

    @FXML
    public void handleAddEvent() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Event");
        dialog.setHeaderText("Enter event details");

        // Gather event details
        dialog.setContentText("Event Title:");
        Optional<String> titleResult = dialog.showAndWait();
        if (titleResult.isPresent()) {
            String title = titleResult.get();

            dialog.setContentText("Event Date (YYYY-MM-DD):");
            Optional<String> dateResult = dialog.showAndWait();
            if (dateResult.isPresent()) {
                String date = dateResult.get();

                dialog.setContentText("Start Time (HHMM):");
                Optional<String> startTimeResult = dialog.showAndWait();
                if (startTimeResult.isPresent()) {
                    int startHHMM = Integer.parseInt(startTimeResult.get());

                    dialog.setContentText("End Time (HHMM):");
                    Optional<String> endTimeResult = dialog.showAndWait();
                    if (endTimeResult.isPresent()) {
                        int endHHMM = Integer.parseInt(endTimeResult.get());

                        dialog.setContentText("Priority (1-5):");
                        Optional<String> priorityResult = dialog.showAndWait();
                        if (priorityResult.isPresent()) {
                            int priority = Integer.parseInt(priorityResult.get());

                            // Create a new event and add it to the calendar
                            Event newEvent = new Event(title, date, startHHMM, endHHMM, priority);
                            CalendarManager.addEvent(newEvent);
                            updateEventTable(); // Call to refresh the table
                        }
                    }
                }
            }
        }
    }

    @FXML
    public void handleViewEvents() {
        refreshEventTable();
    }

    private void updateEventTable() {
        List<Event> events = CalendarManager.getEvents(); // Get the list of events
        eventTable.getItems().clear(); // Clear existing items in the table
        eventTable.getItems().addAll(events); // Add all events to the table
    }

    private void refreshEventTable() {
        updateEventTable(); // Call the update method to refresh the table
    }

    @FXML
    public void handleSaveEvents() {
        CalendarManager.saveEvents(); // Call the save method in CalendarManager
    }
}