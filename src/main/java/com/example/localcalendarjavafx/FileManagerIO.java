package com.example.localcalendarjavafx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManagerIO {

    public static void saveEventsToFile(List<Event> events, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(events);
            System.out.println("Events saved to file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Event> loadEventsFromFile(String filename) {
        List<Event> events = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            events = (List<Event>) ois.readObject(); // This cast can cause an unchecked warning
            System.out.println("Events loaded from file: " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return events;
    }
}