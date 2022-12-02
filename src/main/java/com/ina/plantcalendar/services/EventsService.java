package com.ina.plantcalendar.services;

import com.ina.plantcalendar.database.IDataSource;
import com.ina.plantcalendar.database.RecurringEvent;
import com.ina.plantcalendar.model.AggregatedEventsPerDay;
import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventsService implements IEventsService {

    private ArrayList<Event> events = new ArrayList<>();
    private final IDataSource dataSource;

    @Autowired
    public EventsService(IDataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
    }

    // TODO I need to figure out what will happen if there are several plants under the name;
    public void addRecurringEvent(String plantScientificName, Event.EventType eventType, LocalDate startDate, LocalDate endDate) throws SQLException {
        Plant plant = dataSource.queryPlantByExactScientificName(plantScientificName);
        if (plant == null) {
            System.out.println("Plant not in the DB.");
            return;
        }

        if (dataSource.isEventInDB(plantScientificName, eventType, startDate, endDate)) {
            return;
        } else {
            dataSource.addRecurringEvent(dataSource.queryPlantIdByScientificName(plant.getScientificName()), eventType, startDate, endDate);
        }

        // TODO Add logic for changing the event
    }

    public void addRecurringEvent(String plantScientificName, Event.EventType eventType, LocalDate startDate) throws SQLException {
        Plant plant = dataSource.queryPlantByExactScientificName(plantScientificName);

        if (plant == null) {
            System.out.println("Plant not in the DB.");
            return;
        }

        if (dataSource.isEventInDB(plantScientificName, eventType, startDate, null)) {
            return;
        } else {
            dataSource.addRecurringEvent(dataSource.queryPlantIdByScientificName(plant.getScientificName()), eventType, startDate);
        }

        // TODO Add logic for changing the event
    }

    public List<Event> getEventsForAPlantInTheDateRange(String scientificName, LocalDate from, LocalDate to) throws SQLException {
        List<Event> allFoundEvents = dataSource.findAllEventsForAPlantByDate(from, to, scientificName);
        return allFoundEvents;
    }

    public List<AggregatedEventsPerDay> getUpcomingAggregatedEventsForTheUpcomingWeek() throws SQLException {
        List<Event> allFoundEvents = dataSource.findAllEventsByDate(LocalDate.now(), LocalDate.now().plusDays(6));
        List<AggregatedEventsPerDay> allEventsForTheWeek = new ArrayList<>();
        Map<LocalDate, List<Event>> eventByDay = allFoundEvents.stream().collect(Collectors.groupingBy(Event::getEventDate));

        for (var entry : eventByDay.entrySet()) {
            List<Event> events = entry.getValue();
            List<Plant> plants = new ArrayList<>();
            for (Event event : events) {
                Plant plant = event.getPlant();
                plants.add(plant);
            }
            AggregatedEventsPerDay eventsPerDay = new AggregatedEventsPerDay(entry.getKey(), Event.EventType.WATERING, plants);
            allEventsForTheWeek.add(eventsPerDay);
        }
        return allEventsForTheWeek.stream()
                .sorted(Comparator.comparing(AggregatedEventsPerDay::getDate))
                .collect(Collectors.toList());
    }
}

