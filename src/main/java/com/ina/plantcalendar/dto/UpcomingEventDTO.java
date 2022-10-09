package com.ina.plantcalendar.dto;

import com.ina.plantcalendar.model.Event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UpcomingEventDTO {
    private final List<String> plantNames;
    private final Event.EventType eventType;
    private LocalDate eventDate;

    public UpcomingEventDTO(List<String> plantNames, Event.EventType eventType, LocalDate eventDate) {
        this.plantNames = plantNames;
        this.eventType = eventType;
        this.eventDate = eventDate;
    }

    public Event.EventType getEventType() {
        return eventType;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public List<String> getPlantNames() {
        return plantNames;
    }

    @Override
    public String toString() {
        String dayOfTheWeek = eventDate.getDayOfWeek().toString();
        String dateDayMonth = eventDate.getDayOfMonth() + " " + eventDate.getMonth().toString().substring(0, 1) + eventDate.getMonth().toString().substring(1).toLowerCase();
        return dayOfTheWeek.substring(0, 1) + dayOfTheWeek.substring(1).toLowerCase() + ", " + dateDayMonth + " | " + eventType.toString().substring(0, 1) + eventType.toString().substring(1).toLowerCase() + ": "
                + String.join(", ", plantNames);
    }
}
