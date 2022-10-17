package com.ina.plantcalendar.database;

import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecurringEvent {

    private Plant plant;
    private Event.EventType type;
    // startDate indicates the date of the first event
    private LocalDate startDate;
    // endDate indicates the date by which the recurring events will end. If set to null it means that the event has no end date and is still active.
    private LocalDate endDate;

    public RecurringEvent(Plant plant, Event.EventType type, LocalDate startDate, LocalDate endDate) {
        this.plant = plant;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Event.EventType getType() {
        return type;
    }

    public void setType(Event.EventType type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Event> getAllEventsInTheDateRange(LocalDate from, LocalDate to) {
        List<Event> events = new ArrayList<>();
        int recurrence = plant.getWateringRecurrence();
        // If event has no end date use the end date from the range provided by user
        if(endDate == null) {
            endDate = to;
        }

        if(from.isAfter(endDate) || to.isBefore(startDate)) {
            return List.of();
        } else {
            if(!from.isAfter(startDate)) {
                LocalDate currentEventDate = startDate;
                while(!currentEventDate.isAfter(to) && !currentEventDate.isAfter(endDate)) {
                    events.add(new Event(plant, type, currentEventDate));
                    currentEventDate = currentEventDate.plusDays(recurrence);
                }
                return events;
            } else {
                LocalDate firstEventInTheRange;
                int numberOfDaysBetweenFromDateAndStartDate = from.compareTo(startDate);
                if((numberOfDaysBetweenFromDateAndStartDate%recurrence) == 0) {
                    firstEventInTheRange = startDate.plusDays(numberOfDaysBetweenFromDateAndStartDate);
                } else {
                    int plusDays = (((int)numberOfDaysBetweenFromDateAndStartDate/recurrence)+1)*recurrence;
                    firstEventInTheRange = startDate.plusDays(plusDays);
                }
                LocalDate currentEventDate = firstEventInTheRange;
                while(!currentEventDate.isAfter(to) && !currentEventDate.isAfter(endDate)) {
                    events.add(new Event(plant, type, currentEventDate));
                    currentEventDate = currentEventDate.plusDays(recurrence);
                }
                return events;
            }
        }
    }
}
