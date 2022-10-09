package com.ina.plantcalendar;


import com.ina.plantcalendar.database.IDataSource;
import com.ina.plantcalendar.model.AggregatedEventsPerDay;
import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;
import com.ina.plantcalendar.services.EventsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UpcomingEventsTests {
    private IDataSource dataSource;
    private EventsService eventsService;

    @BeforeEach
    public void setUp() throws SQLException {
        dataSource = Mockito.mock(IDataSource.class);
        eventsService = new EventsService(dataSource);
    }

    @Test
    public void whenThereAreNoEventsThereShouldBeNoResults() throws SQLException {
        // arrange
        Mockito.when(dataSource.findAllEventsByDate(Mockito.any(), Mockito.any()))
                .thenReturn(List.of());

        // act
        var result = eventsService.getUpcomingAggregatedEventsForTheUpcomingWeek();

        // assert
        assertEquals(0, result.size());
    }

    @Test
    public void whenThereIsOneEventThereIsOneGroupWithSinglePlant() throws SQLException {
        Event event = new Event();
        event.setEventDate(LocalDate.now());
        event.setEventType(Event.EventType.WATERING);
        Plant plant = new Plant("plantus scientificus", "scientific plant", Plant.PlantType.SUCCULENT, 1);
        event.setPlant(plant);
        Mockito.when(dataSource.findAllEventsByDate(Mockito.any(), Mockito.any()))
                .thenReturn(List.of(event));

        var result = eventsService.getUpcomingAggregatedEventsForTheUpcomingWeek();

        assertEquals(event.getEventDate(), result.get(0).getDate());
        assertEquals(event.getEventType(), result.get(0).getEventType());
        assertIterableEquals(List.of(plant), result.get(0).getPlants());
    }

    @Test
    public void whenThereAreTwoEventsOnTheSameDayThenThereAreTwoPlantsForTheAggregatedEvent() throws SQLException {
        Plant plant1 = new Plant("plantus scientificus1", "scientific plant1", Plant.PlantType.SUCCULENT, 1);
        Plant plant2 = new Plant("plantus scientificus2", "scientific plant2", Plant.PlantType.SUCCULENT, 1);
        Event event1 = new Event();
        event1.setEventDate(LocalDate.now());
        event1.setEventType(Event.EventType.WATERING);
        event1.setPlant(plant1);
        Event event2 = new Event();
        event2.setEventDate(LocalDate.now());
        event2.setEventType(Event.EventType.WATERING);
        event2.setPlant(plant2);
        List<Event> events = List.of(event1, event2);
        Mockito.when(dataSource.findAllEventsByDate(Mockito.any(), Mockito.any())).thenReturn(events);

        var result = eventsService.getUpcomingAggregatedEventsForTheUpcomingWeek();

        assertEquals(1, result.size());
        assertEquals(events.get(0).getEventDate(), result.get(0).getDate());
        assertEquals(List.of(plant1, plant2), result.get(0).getPlants());
    }

    @Test
    public void whenThereAreTwoEventsOnSeparateDaysThereAreTwoAggregatedEventsWithSinglePlant() throws SQLException {
        Plant plant = new Plant("plantus scientificus", "scientific plant", Plant.PlantType.SUCCULENT, 1);
        Event event1 = new Event();
        event1.setEventDate(LocalDate.now());
        event1.setEventType(Event.EventType.WATERING);
        event1.setPlant(plant);
        Event event2 = new Event();
        event2.setEventDate(LocalDate.now().plusDays(1));
        event2.setEventType(Event.EventType.WATERING);
        event2.setPlant(plant);
        List<Event> events = List.of(event1, event2);
        Mockito.when(dataSource.findAllEventsByDate(Mockito.any(), Mockito.any())).thenReturn(events);

        var result = eventsService.getUpcomingAggregatedEventsForTheUpcomingWeek();

        assertEquals(2, result.size());
        assertEquals(events.get(0).getEventDate(), result.get(0).getDate());
        assertEquals(List.of(plant), result.get(0).getPlants());
        assertEquals(events.get(1).getEventDate(), result.get(1).getDate());
        assertEquals(List.of(plant), result.get(1).getPlants());
    }
}
