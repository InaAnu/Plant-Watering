package com.ina.plantcalendar.database;

import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
public class RecurringEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "_id", nullable=false)
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plant_id", referencedColumnName = "_id", nullable=false)
    private Plant plant;
    @Column(name = "type", nullable=false)
    @Enumerated(EnumType.STRING)
    private Event.EventType type;
    // startDate indicates the date of the first event
    @Column(name = "start_date", nullable=false)
    private LocalDateTime startDate;
    // endDate indicates the date by which the recurring events will end. If set to null it means that the event has no end date and is still active.
    @Column(name = "end_date", nullable=true)
    private LocalDateTime endDate;


    public RecurringEvent() {
    }

    public RecurringEvent(Plant plant, Event.EventType type, LocalDate startDate, LocalDate endDate) {
        this.plant = plant;
        this.type = type;
        this.startDate = startDate.atStartOfDay();
        if(endDate == null) {
            this.endDate = null;
        } else {
            this.endDate = endDate.atStartOfDay();
        }
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
        return startDate.toLocalDate();
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate.atStartOfDay();
    }

    public LocalDate getEndDate() {
        if(this.endDate == null) {
            return null;
        } else {
            return endDate.toLocalDate();
        }
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate.atStartOfDay();
    }

//    TODO correct this method
    public List<Event> getAllEventsInTheDateRange(LocalDate from, LocalDate to) {
        List<Event> events = new ArrayList<>();
        int recurrence = plant.getWateringRecurrence();
        // If event has no end date use the end date from the range provided by user
        if(getEndDate() == null) {
            setEndDate(to);
        }

        LocalDate startDate = getStartDate();
        if(from.isAfter(getEndDate()) || to.isBefore(getStartDate())) {
            return List.of();
        } else {
            if(!from.isAfter(getStartDate())) {
                LocalDate currentEventDate = getStartDate();
                while(!currentEventDate.isAfter(to) && !currentEventDate.isAfter(getEndDate())) {
                    events.add(new Event(plant, type, currentEventDate));
                    currentEventDate = currentEventDate.plusDays(recurrence);
                }
                return events;
            } else {
                LocalDate firstEventInTheRange;
                int numberOfDaysBetweenFromDateAndStartDate = (int) getStartDate().until(from, ChronoUnit.DAYS);
                if((numberOfDaysBetweenFromDateAndStartDate%recurrence) == 0) {
                    firstEventInTheRange = getStartDate().plusDays(numberOfDaysBetweenFromDateAndStartDate);
                } else {
                    int plusDays = (((int)numberOfDaysBetweenFromDateAndStartDate/recurrence)+1)*recurrence;
                    firstEventInTheRange = getStartDate().plusDays(plusDays);
                }
                LocalDate currentEventDate = firstEventInTheRange;
                while(!currentEventDate.isAfter(to) && !currentEventDate.isAfter(getEndDate())) {
                    events.add(new Event(plant, type, currentEventDate));
                    currentEventDate = currentEventDate.plusDays(recurrence);
                }
                return events;
            }
        }
    }
}
