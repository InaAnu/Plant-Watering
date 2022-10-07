package com.ina.plantcalendar.model;

import java.time.LocalDate;

public class Event {

    private Plant plant;
    private EventType eventType;
    private LocalDate lastWateredOn;
    private LocalDate eventDate;
//    public LocalDate today = LocalDate.now();

    public enum EventType {
        WATERING, FERTILIZING, REPLANTING
    }

    public Event() {
    }

    public Event(Plant plant, EventType eventType, LocalDate lastWateredOn) {
        this.plant = plant;
        this.eventType = eventType;
        int wateringRecurrence = plant.getWateringRecurrence();
        this.eventDate = getEventDate(lastWateredOn,wateringRecurrence);
    }

    // TODO Change to abstract class, to change behaviour depending on what type of event we have.

    public LocalDate getEventDate() {
        return eventDate;
    }

    public LocalDate getEventDate(LocalDate lastWateredOn, int wateringRecurrence) {
        return lastWateredOn.plusDays(wateringRecurrence);
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public LocalDate getLastWateredOn() {
        return lastWateredOn;
    }

    public void setLastWateredOn(LocalDate lastWateredOn) {
        this.lastWateredOn = lastWateredOn;
    }

    @Override
    public String toString() {
        String dayOfTheWeek = eventDate.getDayOfWeek().toString();
        String dateDayMonth = eventDate.getDayOfMonth() + " " + eventDate.getMonth().toString().substring(0,1) + eventDate.getMonth().toString().substring(1).toLowerCase();
        return dayOfTheWeek.substring(0,1) + dayOfTheWeek.substring(1).toLowerCase() + ", " + dateDayMonth + " | " + eventType.toString().substring(0,1) + eventType.toString().substring(1).toLowerCase() + ": " + plant.getAlias();
    }
}
