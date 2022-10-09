package com.ina.plantcalendar.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AggregatedEventsPerDay {

    private LocalDate date;
    private Event.EventType eventType;
    private List<Plant> plants = new ArrayList<>();

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
        return "AggregatedEventsPerDay{" +
                "date=" + date +
                ", eventType=" + eventType +
                ", plants=" + plants.stream().map(Plant::getAlias).collect(Collectors.joining(", ")) +
                '}';
    }
}
