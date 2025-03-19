package com.example.localcalendarjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public void handleEditButtonAction() {
        Event selectedEvent = eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            editEvent(selectedEvent); // Call the edit method
        } else {
            System.out.println("No event selected for editing.");
        }
    }

    private void editEvent(Event event) {
        Dialog<Event> dialog = new Dialog<>();
        dialog.setTitle("Edit Event");

        // Create fields for title, date, start time, end time, and priority
        TextField titleField = new TextField(event.getTitle());
        TextField dateField = new TextField(event.getDate());
        TextField startTimeField = new TextField(String.valueOf(event.getStartTime()));
        TextField endTimeField = new TextField(String.valueOf(event.getEndTime()));
        TextField priorityField = new TextField(String.valueOf(event.getPriority()));

        // Set up the dialog layout
        VBox vbox = new VBox(titleField, dateField, startTimeField, endTimeField, priorityField);
        dialog.getDialogPane().setContent(vbox);

        // Add buttons for confirmation
        ButtonType confirmButton = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);

        // Handle the result
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButton) {
                // Create new event with updated values
                Event updatedEvent = new Event(
                        titleField.getText(),
                        dateField.getText(),
                        Integer.parseInt(startTimeField.getText()),
                        Integer.parseInt(endTimeField.getText()),
                        Integer.parseInt(priorityField.getText())
                );

                // Check for conflicts using the priority handler
                if (PriorityCollisionHandler.hasConflict(updatedEvent, CalendarManager.getEvents())) {
                    showConflictAlert();
                    return null; // Return null to indicate no update
                }

                // Update the event
                eventListView.getItems().remove(event); // Remove old event from the list
                CalendarManager.addEvent(updatedEvent); // Add updated event
                displayEvents(); // Refresh the ListView
                return updatedEvent;
            }
            return null;
        });

        dialog.showAndWait();
    }

    // Method to show conflict alert
    private void showConflictAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Conflict Detected");
        alert.setHeaderText("Event Conflict");
        alert.setContentText("The event you are trying to edit conflicts with an existing event.");
        alert.showAndWait();
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