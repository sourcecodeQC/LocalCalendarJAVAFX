package com.example.localcalendarjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;
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
    public Button exitButton;
    @FXML
    public Button sortToggleButton; // Button for toggling sort
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

    private enum SortOrder {
        ORIGINAL, ASCENDING, DESCENDING
    }

    private SortOrder currentSortOrder = SortOrder.ORIGINAL; // Track current sort order

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

    @FXML
    public void handleExitButtonAction() {
        List<Event> allEvents = eventListView.getItems();
        FileManagerIO.saveEvents(allEvents); // Save all displayed events
        System.out.println("All displayed events saved successfully.");
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleSortToggleButtonAction() {
        switch (currentSortOrder) {
            case ORIGINAL:
                currentSortOrder = SortOrder.ASCENDING; // Change to ascending
                sortToggleButton.setText("Sort Descending");
                break;
            case ASCENDING:
                currentSortOrder = SortOrder.DESCENDING; // Change to descending
                sortToggleButton.setText("Show Original Order");
                break;
            case DESCENDING:
                currentSortOrder = SortOrder.ORIGINAL; // Change to original
                sortToggleButton.setText("Sort Ascending");
                break;
        }
        updateEventListView(); // Update the ListView based on the current sort order
    }

    private void updateEventListView() {
        Event[] eventsToDisplay;
        switch (currentSortOrder) {
            case ASCENDING:
                eventsToDisplay = CalendarManager.sortGUIPriority(true); // Sort ascending
                break;
            case DESCENDING:
                eventsToDisplay = CalendarManager.sortGUIPriority(false); // Sort descending
                break;
            case ORIGINAL:
            default:
                eventsToDisplay = CalendarManager.getEvents().toArray(new Event[0]); // Original order
                break;
        }
        eventListView.getItems().clear(); // Clear existing items
        eventListView.getItems().addAll(Arrays.asList(eventsToDisplay)); // Add events to the ListView
    }
}