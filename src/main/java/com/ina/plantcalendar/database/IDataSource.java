package com.ina.plantcalendar.database;

import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface IDataSource {
    boolean createViewForPlantInfo() throws SQLException;

    boolean createViewForFullEventInfo() throws SQLException;

    List<Plant> queryPlantByName(String name) throws SQLException;

    Plant queryPlantByExactScientificName(String exactScientificName) throws SQLException;

    List<Plant> queryPlants() throws SQLException;

    int queryPlantIdByScientificName(String scientificName) throws SQLException;

    boolean isEventInDB(String scientificName, Event.EventType eventType) throws SQLException;

    boolean addRecurringEvent(int plantId, Event.EventType eventType, LocalDate lastWateredOn, LocalDate startDate, LocalDate endDate) throws SQLException;

    boolean addRecurringEvent(int plantId, Event.EventType eventType, LocalDate lastWateredOn, LocalDate startDate) throws SQLException;

    default List<Event> findAllEventsByDate(LocalDate from, LocalDate to) {
        return List.of();
    }
}
