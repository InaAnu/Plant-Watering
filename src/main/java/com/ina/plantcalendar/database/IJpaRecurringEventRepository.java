package com.ina.plantcalendar.database;

import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IJpaRecurringEventRepository extends CrudRepository<RecurringEvent, Integer> {

    @Query("SELECT e FROM RecurringEvent e WHERE e.plant.id = ?1 AND e.type = ?2")
    List<RecurringEvent> findByPlantIdAndEventType(int plantId, Event.EventType eventType);

    //TODO Check if this method gives correct results
    @Query("SELECT e FROM RecurringEvent e WHERE (e.startDate <= ?2) AND (e.endDate IS NULL OR e.endDate >= ?1)")
    List<RecurringEvent> findInTheDateRange(LocalDateTime from, LocalDateTime to);

    @Query("SELECT e FROM RecurringEvent e WHERE e.plant.scientificName = ?1 AND e.startDate <= ?3 AND e.endDate IS NULL OR e.endDate >= ?2")
    List<RecurringEvent> findByScientificNameInTheDateRange(String scientificName, LocalDate from, LocalDate to);

    @Query("SELECT e FROM RecurringEvent e WHERE e.plant.id = ?1 AND e.type = ?2 AND e.startDate <= ?4 AND e.endDate IS NULL OR e.endDate >= ?3")
    List<RecurringEvent> findByPlantIdAndEventTypeInTheDateRange(int plantId, Event.EventType eventType, LocalDate from, LocalDate to);


//    @Transactional
//    @Modifying
//    @Query("INSERT INTO RecurringEvent e VALUES(:e.plant.id, :e.type, :e.startDate, :e.endDate)")
//    int addRecurringEventWithEndDate(int plantId, Event.EventType type, LocalDate startDate, LocalDate endDate);
//
//    @Transactional
//    @Modifying
//    @Query("INSERT INTO RecurringEvent e VALUES(:e.plant.id, :e.type, :e.startDate)")
//    int addRecurringEventWithoutEndDate(int plantId, Event.EventType type, LocalDate startDate);
}
