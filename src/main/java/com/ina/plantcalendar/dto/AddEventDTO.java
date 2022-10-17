package com.ina.plantcalendar.dto;

import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;

import java.time.LocalDate;

public class AddEventDTO {

    private Plant plant;
    private Event.EventType eventType;
    private LocalDate lastWateredOn;

    public AddEventDTO(Plant plant, Event.EventType eventType, LocalDate lastWateredOn) {
        this.plant = plant;
        this.eventType = eventType;
        this.lastWateredOn = lastWateredOn;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Event.EventType getEventType() {
        return eventType;
    }

    public void setEventType(Event.EventType eventType) {
        this.eventType = eventType;
    }

    public LocalDate getLastWateredOn() {
        return lastWateredOn;
    }

    public void setLastWateredOn(LocalDate lastWateredOn) {
        this.lastWateredOn = lastWateredOn;
    }

    public LocalDate getEventDate(LocalDate lastWateredOn) {
        int wateringRecurrence = plant.getWateringRecurrence();
        return lastWateredOn.plusDays(wateringRecurrence);
    }
}
