package com.ina.plantcalendar.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Event {

    private ArrayList<String> plantNames;
    private EventType eventType;
    private LocalDate date;

    public enum EventType {
        WATERING, FERTILIZING, REPLANTING
    }

    public Event(ArrayList<String> plantNames, EventType eventType, LocalDate date) {
        this.plantNames = plantNames;
        this.eventType = eventType;
        this.date = date;
    }

    // TODO Figure out what I need here. XDDD
    // TODO Change to abstract class, to change behaviour depending on what type of event we have.


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder plantsForTheEvent = new StringBuilder();
        for (String plantName:plantNames) {
            if(plantsForTheEvent.length() != 0) {
                plantsForTheEvent.append(", ");
            }
            plantsForTheEvent.append(plantName);
        }
        String dayOfTheWeek = date.getDayOfWeek().toString();
        String dateDayMonth = date.getDayOfMonth() + " " + date.getMonth().toString().substring(0,1) + date.getMonth().toString().substring(1).toLowerCase();
        // return eventType.toString().substring(0,1) + eventType.toString().substring(1).toLowerCase() + ": " + date.getDayOfMonth() + " " + date.getMonth().toString().substring(0,1) + date.getMonth().toString().substring(1).toLowerCase() + " " + date.getYear();
        return dayOfTheWeek.toString().substring(0,1) + dayOfTheWeek.toString().substring(1).toLowerCase() + ", " + dateDayMonth + " | " + eventType.toString().substring(0,1) + eventType.toString().substring(1).toLowerCase() + ": " + plantsForTheEvent;
    }
    // TODO Can I do this in a simpler way to get the WATERING and JUNE to be Watering and June?
}
