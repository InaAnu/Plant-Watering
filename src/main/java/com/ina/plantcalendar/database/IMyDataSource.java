package com.ina.plantcalendar.database;

import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface IMyDataSource {

    Plant queryPlantByExactScientificName(String exactScientificName) throws SQLException;

    List<Plant> queryPlants() throws SQLException;

    int queryPlantIdByScientificName(String scientificName) throws SQLException;

    boolean isEventInDB(Plant plant, Event.EventType eventType, LocalDate from, LocalDate to);

    void addPlant(String scientificName, String alias, Plant.PlantType type, int wateringRecurrence);

    void addRecurringEvent(Plant plant, Event.EventType eventType, LocalDate startDate);

    default List<Event> findAllEventsByDate(LocalDate from, LocalDate to) {
        return List.of();
    }

    default List<Event> findAllEventsForAPlantByDate(LocalDate from, LocalDate to, String scientificName) {
        return List.of();
    }
}
