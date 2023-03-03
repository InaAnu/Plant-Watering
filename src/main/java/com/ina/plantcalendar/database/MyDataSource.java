package com.ina.plantcalendar.database;

import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class MyDataSource implements IMyDataSource {

    private final IJpaRecurringEventRepository jpaRecurringEventRepo;
    private final IJpaPlantRepository jpaPlantRepo;

    @Autowired
    public MyDataSource(IJpaRecurringEventRepository jpaRecurringEventRepo, IJpaPlantRepository jpaPlantRepo) {
        this.jpaRecurringEventRepo = jpaRecurringEventRepo;
        this.jpaPlantRepo = jpaPlantRepo;
    }

    // TODO Make a way to add plants to the database

    @Override
    public Plant queryPlantByExactScientificName(String exactScientificName) {
        return jpaPlantRepo.queryByScientificName(exactScientificName);
    }

    @Override
    public Plant queryPlantByExactScientificNameOrExactAlias(String name) {
        return jpaPlantRepo.queryByExactScientificNameOrExactAlias(name);
    }

    @Override
    public List<Plant> queryPlants() {
        return (List<Plant>) jpaPlantRepo.findAll();
    }

    @Override
    public int queryPlantIdByScientificName(String scientificName) {
        return jpaPlantRepo.queryIdByScientificName(scientificName);
    }

    @Override
    public boolean isEventInDB(Plant plant, Event.EventType eventType, LocalDate from, LocalDate to) {
        List<RecurringEvent> recurringEvents = jpaRecurringEventRepo.findByScientificNameAndEventTypeInTheDateRange(plant.getScientificName(), eventType, from, to);

        if (recurringEvents.size() != 0) {
            System.out.println("Event is already in the database.");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addPlant(String scientificName, String alias, Plant.PlantType type, int wateringRecurrence) {
        jpaPlantRepo.save(new Plant(scientificName, alias, type, wateringRecurrence));
    }

    @Override
    public void addRecurringEvent(Plant plant, Event.EventType eventType, LocalDate startDate) {
        jpaRecurringEventRepo.save(new RecurringEvent(plant,eventType, startDate, null));
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
