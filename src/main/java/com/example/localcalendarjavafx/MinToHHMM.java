package com.example.localcalendarjavafx;

public class MinToHHMM {

    public static int minToHHMM(int minutes) {
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;
        return hours * 100 + remainingMinutes;
    }

    public static int hhmmToMin(int hhmm) {
        int hours = hhmm / 100;
        int minutes = hhmm % 100;
        return hours * 60 + minutes;
    }
}