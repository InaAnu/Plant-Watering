package com.ina.plantcalendar.services;

import com.ina.plantcalendar.database.IMyDataSource;
import com.ina.plantcalendar.dto.EventDTO;
import com.ina.plantcalendar.model.AggregatedEventsPerDay;
import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
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

    public boolean saveRecurringEvent(EventDTO eventDTO) throws SQLException {
        boolean isSaved = false;
        if (dataSource.queryPlantByExactScientificNameOrExactAlias(eventDTO.getPlantName()) == null) {
            return isSaved;
        }
        Plant plant = dataSource.queryPlantByExactScientificNameOrExactAlias(eventDTO.getPlantName());
        Event.EventType eventType = Event.EventType.valueOf(eventDTO.getType());
        LocalDate eventDate = calculateEventDate(plant, eventDTO.getLastWateredOn());
        if (!dataSource.isEventInDB(plant,eventType,eventDate,null)) {
            dataSource.addRecurringEvent(plant, eventType, eventDate);
            isSaved = true;
        }
        return isSaved;
    }

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

    public LocalDate calculateEventDate(Plant plant, LocalDate lastWateredOn) {
        int wateringRecurrence = plant.getWateringRecurrence();
        return lastWateredOn.plusDays(wateringRecurrence);
    }
}

