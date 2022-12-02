package com.ina.plantcalendar.database;

import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class DataSource implements IDataSource {

    private final IJpaRecurringEventRepository jpaRecurringEventRepo;
    private final IJpaPlantRepository jpaPlantRepo;

    @Autowired
    public DataSource(IJpaRecurringEventRepository jpaRecurringEventRepo, IJpaPlantRepository jpaPlantRepo) {
        this.jpaRecurringEventRepo = jpaRecurringEventRepo;
        this.jpaPlantRepo = jpaPlantRepo;
    }

    // TODO Make a way to add plants to the database

    @Override
    public boolean isEventInDB(String scientificName, Event.EventType eventType, LocalDate from, LocalDate to) {
        int plantId = jpaPlantRepo.queryIdByScientificName(scientificName);
        List<RecurringEvent> recurringEvents = jpaRecurringEventRepo.findByPlantIdAndEventTypeInTheDateRange(plantId, eventType, from, to);

        if (recurringEvents != null) {
            System.out.println("Event is already in the database.");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addRecurringEvent(int plantId, Event.EventType eventType, LocalDate startDate, LocalDate endDate) {

        boolean isUpdated = false;
        int rows = jpaRecurringEventRepo.addRecurringEventWithEndDate(plantId, eventType, startDate, endDate);
        if (rows > 0) {
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean addRecurringEvent(int plantId, Event.EventType eventType, LocalDate startDate) {

        boolean isUpdated = false;
        int rows = jpaRecurringEventRepo.addRecurringEventWithoutEndDate(plantId, eventType, startDate);
        if (rows > 0) {
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public List<Event> findAllEventsForAPlantByDate(LocalDate from, LocalDate to, String scientificName) {

        List<Event> events = new ArrayList<>();
        List<RecurringEvent> recurringEvents = jpaRecurringEventRepo.findByScientificNameInTheDateRange(scientificName,from, to);
        for (var recurringEvent : recurringEvents) {
            List<Event> eventsFromTheRecurringEvent = recurringEvent.getAllEventsInTheDateRange(from,to);
            events.addAll(eventsFromTheRecurringEvent);
        }
        return events;
    }

    @Override
    public List<Event> findAllEventsByDate(LocalDate from, LocalDate to) {

        List<Event> events = new ArrayList<>();
        List<RecurringEvent> recurringEvents = jpaRecurringEventRepo.findInTheDateRange(from, to);
        for (var recurringEvent : recurringEvents) {
            List<Event> eventsFromTheRecurringEvent = recurringEvent.getAllEventsInTheDateRange(from,to);
            events.addAll(eventsFromTheRecurringEvent);
        }
        return events;
    }
}
