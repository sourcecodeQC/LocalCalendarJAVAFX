package com.example.localcalendarjavafx;

public class Event {
    private final String title;
    private final String date; // Assuming date is a String for simplicity
    private final int startTime; // Start time in minutes
    private final int endTime; // End time in minutes
    private final int priority;

    public Event(String title, String date, int startTime, int endTime, int priority) {
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getPriority() {
        return priority;
    }

    // Method to get start time in HHMM format
    public String getStartHHMM() {
        int hours = startTime / 60;
        int minutes = startTime % 60;
        return String.format("%02d%02d", hours, minutes);
    }

    // Method to get end time in HHMM format
    public String getEndHHMM() {
        int hours = endTime / 60;
        int minutes = endTime % 60;
        return String.format("%02d%02d", hours, minutes);
    }

    @Override
    public String toString() {
        return String.format("%s on %s from %s to %s (Priority: %d)", title, date, getStartHHMM(), getEndHHMM(), priority);
    }
}