package com.ina.plantcalendar.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AggregatedEventsPerDay {

    private LocalDate date;
    private Event.EventType eventType;
    private List<Plant> plants;

    public AggregatedEventsPerDay(LocalDate date, Event.EventType eventType, List<Plant> plants) {
        this.date = date;
        this.eventType = eventType;
        this.plants = plants;
    }

    public LocalDate getDate() {
        return date;
    }

    public Event.EventType getEventType() {
        return eventType;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    @Override
    public String toString() {
        String dayOfTheWeek = date.getDayOfWeek().toString();
        String dayOfTheWeekCapitalized = dayOfTheWeek.substring(0,1) + dayOfTheWeek.substring(1).toLowerCase();
        String dateDayMonthCapitalized = date.getDayOfMonth() + " " + date.getMonth().toString().substring(0,1) + date.getMonth().toString().substring(1).toLowerCase();
        String eventTypeCapitalized = eventType.toString().substring(0,1) + eventType.toString().substring(1).toLowerCase();
        return dayOfTheWeekCapitalized + ", " + dateDayMonthCapitalized + " | " + eventTypeCapitalized + ": " +
                plants.stream().map(Plant::getAlias).collect(Collectors.joining(", "));
    }
}
