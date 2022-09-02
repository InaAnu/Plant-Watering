package com.ina.plantcalendar.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class AggregatedEventsPerDay {

    private LocalDate date;
    private Event.EventType eventType;
    DataSource dataSource = new DataSource();

    private ArrayList<String> plantsForTheEvent = new ArrayList<>();

    public AggregatedEventsPerDay(LocalDate date, Event.EventType eventType) {
        this.date = date;
        this.eventType = eventType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Event.EventType getEventType() {
        return eventType;
    }

    public void setEventType(Event.EventType eventType) {
        this.eventType = eventType;
    }

    public ArrayList<String> getPlantsForTheEvent() {
        return plantsForTheEvent;
    }

    public void setPlantsForTheEvent(ArrayList<String> plantsForTheEvent) {
        this.plantsForTheEvent = plantsForTheEvent;
    }

    @Override
    public String toString() {
        StringBuilder plantsNameList = new StringBuilder();
        for (String plantName:plantsForTheEvent) {
            if(plantsNameList.length() != 0) {
                plantsNameList.append(", ");
            }
            plantsNameList.append(plantName);
        }
        String dayOfTheWeek = date.getDayOfWeek().toString();
        String dateDayMonth = date.getDayOfMonth() + " " + date.getMonth().toString().substring(0,1) + date.getMonth().toString().substring(1).toLowerCase();
        return dayOfTheWeek.toString().substring(0,1) + dayOfTheWeek.toString().substring(1).toLowerCase() + ", " + dateDayMonth + " | " + eventType.toString().substring(0,1) + eventType.toString().substring(1).toLowerCase() + ": " + plantsForTheEvent;
    }
    // TODO Can I do this in a simpler way to get the WATERING and JUNE to be Watering and June?
}
