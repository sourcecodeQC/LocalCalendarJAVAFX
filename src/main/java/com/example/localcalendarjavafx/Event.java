package com.example.localcalendarjavafx;

public class Event {
    private String title;
    private String date; // Format: YYYY-MM-DD
    private int startHHMM; // Start time in HHMM format
    private int endHHMM; // End time in HHMM format
    private int priority; // Priority as an integer

    public Event(String title, String date, int startHHMM, int endHHMM, int priority) {
        this.title = title;
        this.date = date;
        this.startHHMM = startHHMM;
        this.endHHMM = endHHMM;
        this.priority = priority;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public int getStartHHMM() {
        return startHHMM;
    }

    public int getEndHHMM() {
        return endHHMM;
    }

    public int getPriority() {
        return priority;
    }

    public int getStartMin() {
        return MinToHHMM.hhmmToMin(startHHMM);
    }

    public int getEndMin() {
        return MinToHHMM.hhmmToMin(endHHMM);
    }

    @Override
    public String toString() {
        return String.format("Event: %s, Date: %s, Start: %04d, End: %04d, Priority: %d",
                title, date, startHHMM, endHHMM, priority);
    }
}