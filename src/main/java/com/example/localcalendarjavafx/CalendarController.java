package com.example.localcalendarjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class CalendarController {
    @FXML
    private Button saveButton;
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
    public void handleSaveButtonAction() {
        String title = eventTitleField.getText();
        String date = eventDateField.getText();
        int startTime = HourToMin.convertToMin(eventStartTimeField.getText()); // Convert HHMM to minutes
        int endTime = HourToMin.convertToMin(eventEndTimeField.getText()); // Convert HHMM to minutes
        int priority = Integer.parseInt(eventPriorityField.getText());

        Event newEvent = new Event(title, date, startTime, endTime, priority); // Create new event
        CalendarManager.addEvent(newEvent); // Add event to manager
        CalendarManager.saveEvents(); // Save events using the original method
        displayEvents(); // Refresh the event display
    }

    @FXML
    public void initialize() {
        CalendarManager.loadEvents(); // Load events on initialization
        displayEvents(); // Display loaded events
    }

    private void displayEvents() {
        List<Event> events = CalendarManager.getEvents(); // Get events from manager
        eventListView.getItems().clear(); // Clear existing items
        eventListView.getItems().addAll(events); // Add all events to the ListView
    }
}