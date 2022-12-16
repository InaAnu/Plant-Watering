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

    boolean isEventInDB(String scientificName, Event.EventType eventType, LocalDate from, LocalDate to);

//    boolean addRecurringEvent(int plantId, Event.EventType eventType, LocalDate startDate, LocalDate endDate) throws SQLException;
//
//    boolean addRecurringEvent(int plantId, Event.EventType eventType, LocalDate startDate) throws SQLException;

    default List<Event> findAllEventsByDate(LocalDate from, LocalDate to) {
        return List.of();
    }

    default List<Event> findAllEventsForAPlantByDate(LocalDate from, LocalDate to, String scientificName) {
        return List.of();
    }
}
