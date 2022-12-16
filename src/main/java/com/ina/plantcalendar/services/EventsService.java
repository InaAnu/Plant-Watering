package com.ina.plantcalendar.services;

import com.ina.plantcalendar.database.IMyDataSource;
import com.ina.plantcalendar.model.AggregatedEventsPerDay;
import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventsService {

    private final IMyDataSource dataSource;

    @Autowired
    public EventsService(IMyDataSource dataSource) {
        this.dataSource = dataSource;
    }

//    public void addRecurringEvent(String plantScientificName, Event.EventType eventType, LocalDate startDate, LocalDate endDate) throws SQLException {
//        Plant plant = dataSource.queryPlantByExactScientificName(plantScientificName);
//        if (plant == null) {
//            System.out.println("Plant not in the DB.");
//            return;
//        }
//
//        if (dataSource.isEventInDB(plantScientificName, eventType, startDate, endDate)) {
//            return;
//        } else {
//            dataSource.addRecurringEvent(dataSource.queryPlantIdByScientificName(plant.getScientificName()), eventType, startDate, endDate);
//        }
//
//        // TODO Add logic for changing the event
//    }
//
//    public void addRecurringEvent(String plantScientificName, Event.EventType eventType, LocalDate startDate) throws SQLException {
//        Plant plant = dataSource.queryPlantByExactScientificName(plantScientificName);
//
//        if (plant == null) {
//            System.out.println("Plant not in the DB.");
//            return;
//        }
//
//        if (dataSource.isEventInDB(plantScientificName, eventType, startDate, null)) {
//            return;
//        } else {
//            dataSource.addRecurringEvent(dataSource.queryPlantIdByScientificName(plant.getScientificName()), eventType, startDate);
//        }
//
//        // TODO Add logic for changing the event
//    }

    public List<Event> getEventsForAPlantInTheDateRange(String scientificName, LocalDate from, LocalDate to) {
        List<Event> allFoundEvents = dataSource.findAllEventsForAPlantByDate(from, to, scientificName);
        return allFoundEvents;
    }

    public List<AggregatedEventsPerDay> getUpcomingAggregatedEventsForTheUpcomingWeek() {
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

