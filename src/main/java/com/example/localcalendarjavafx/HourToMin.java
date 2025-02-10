package com.example.localcalendarjavafx;

public class HourToMin {
    public static int convertToMin(String hhmm) {
        int hours = Integer.parseInt(hhmm.substring(0, 2));
        int minutes = Integer.parseInt(hhmm.substring(2, 4));
        return hours * 60 + minutes;
    }
}