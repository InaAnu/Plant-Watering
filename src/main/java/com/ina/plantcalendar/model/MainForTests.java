package com.ina.plantcalendar.model;

import com.ina.plantcalendar.database.DataSource;
import com.ina.plantcalendar.services.EventsService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainForTests {
    public static void main(String[] args) throws SQLException {

        DataSource dataSource = new DataSource();

//        dataSource.createViewForPlantInfo();
//        dataSource.createViewForFullEventInfo();

//        EventsService eventsService = new EventsService(dataSource);
//        eventsService.addRecurringEvent("Ocimum Basilicum", Event.EventType.WATERING, LocalDate.now().plusDays(2));

//        EventsService eventsService1 = new EventsService(dataSource);
//        eventsService1.addRecurringEvent("Parthenocissus Striata", Event.EventType.WATERING, LocalDate.now().minusDays(3));


        List<Event> events = dataSource.findAllEventsByDate(LocalDate.of(2022, 10, 9), LocalDate.of(2022, 10, 25));
        EventsService eventsService = new EventsService(dataSource);
        List<AggregatedEventsPerDay> aggregatedEventsPerDay = eventsService.getUpcomingAggregatedEventsForTheUpcomingWeek();
        for (var event:aggregatedEventsPerDay) {
            System.out.println(event);
        }

        List<Event> eventsForAPlant = dataSource.findAllEventsForAPlantByDate(LocalDate.of(2022, 10, 9), LocalDate.of(2022, 10, 25), "Ocimum Basilicum" );
        for (var event:eventsForAPlant) {
            System.out.println(event);
        }

    }

}
