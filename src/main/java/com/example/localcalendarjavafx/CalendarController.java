package com.example.localcalendarjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class CalendarController {

    public Button addEventButton;
    @FXML
    public Button saveAllButton;
    @FXML
    public Button deleteButton; // New delete button
    @FXML
    public Button refreshButton; // New refresh button
    @FXML
    private TextField eventTitleField; // Field for event title
    @FXML
    private TextField eventDateField; // Field for event date
    @FXML
    private TextField eventStartTimeField; // Field for start time in HHMM format
    @FXML
    private TextField eventEndTimeField; // Field for end time in HHMM format
    @FXML
    private TextField eventPriorityField; // Field for event priority
    @FXML
    private ListView<Event> eventListView; // ListView to display events

    @FXML
    public void initialize() {
        CalendarManager.loadEvents(); // Load events on initialization
        displayEvents(); // Display loaded events
    }

    @FXML
    public void handleAddButtonAction() {
        String title = eventTitleField.getText();
        String date = eventDateField.getText();
        int startTime = HourToMin.convertToMin(eventStartTimeField.getText()); // Convert HHMM to minutes
        int endTime = HourToMin.convertToMin(eventEndTimeField.getText()); // Convert HHMM to minutes
        int priority = Integer.parseInt(eventPriorityField.getText());

        Event newEvent = new Event(title, date, startTime, endTime, priority); // Create new event
        PriorityCollisionHandler.handleEventAddition(newEvent, CalendarManager.getEvents(), eventListView); // Check for collisions
    }

    @FXML
    public void handleDeleteButtonAction() {
        Event selectedEvent = eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            CalendarManager.getEvents().remove(selectedEvent); // Remove from the manager
            FileManagerIO.saveEvents(CalendarManager.getEvents()); // Save updated events
            displayEvents(); // Refresh the ListView
            System.out.println("Event deleted successfully.");
        } else {
            System.out.println("No event selected for deletion.");
        }
    }

    @FXML
    public void handleRefreshButtonAction() {
        refreshEvents(); // Manually refresh the events
    }

    private void refreshEvents() {
        CalendarManager.loadEvents(); // Reload events from the file
        displayEvents(); // Refresh the ListView
    }

    private void displayEvents() {
        List<Event> events = CalendarManager.getEvents(); // Get events from manager
        eventListView.getItems().clear(); // Clear existing items
        eventListView.getItems().addAll(events); // Add all events to the ListView
    }

    public void handleSaveAllEvents() {
        List<Event> allEvents = CalendarManager.getEvents(); // Assuming this method retrieves all events
        FileManagerIO.saveEvents(allEvents); // Save all events to file
        System.out.println("All events saved successfully.");
    }
}