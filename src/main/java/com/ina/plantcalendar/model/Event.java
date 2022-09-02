package com.ina.plantcalendar.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class Event {

    private Plant plant;
    private EventType eventType;
    private LocalDate lastWateredOn;
    private LocalDate nextOccurrence;
//    public LocalDate today = LocalDate.now();
    private ArrayList<Plant> plants = new ArrayList<>();

    public enum EventType {
        WATERING, FERTILIZING, REPLANTING
    }

    public Event(Plant plant, EventType eventType, LocalDate lastWateredOn) {
        this.plant = plant;
        this.eventType = eventType;
        int wateringRecurrence = plant.getWateringRecurrence();
        this.nextOccurrence = getNextOccurrence(lastWateredOn,wateringRecurrence);
    }

    // TODO Change to abstract class, to change behaviour depending on what type of event we have.

    public LocalDate getNextOccurrence(LocalDate lastWateredOn, int wateringRecurrence) {
        return lastWateredOn.plusDays(wateringRecurrence);
    }

    public void setNextOccurrence(LocalDate nextOccurrence) {
        this.nextOccurrence = nextOccurrence;
    }

    @Override
    public String toString() {
        String dayOfTheWeek = nextOccurrence.getDayOfWeek().toString();
        String dateDayMonth = nextOccurrence.getDayOfMonth() + " " + nextOccurrence.getMonth().toString().substring(0,1) + nextOccurrence.getMonth().toString().substring(1).toLowerCase();
        return dayOfTheWeek.substring(0,1) + dayOfTheWeek.substring(1).toLowerCase() + ", " + dateDayMonth + " | " + eventType.toString().substring(0,1) + eventType.toString().substring(1).toLowerCase() + ": " + plant.getAlias();
    }
}
