package com.example.localcalendarjavafx;

public class Event {
    private String title;
    private String date; // Assuming date is a String for simplicity
    private int startTime; // Start time in minutes
    private int endTime; // End time in minutes
    private int priority;

    // Updated constructor to match the parameters
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

    @Override
    public String toString() {
        return String.format("%s on %s from %d to %d (Priority: %d)", title, date, startTime, endTime, priority);
    }
}