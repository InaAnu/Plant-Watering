package com.ina.plantcalendar.model;

import com.ina.plantcalendar.database.DataSource;
import com.ina.plantcalendar.services.EventsService;

import java.sql.SQLException;
import java.time.LocalDate;

public class MainForTests {
    public static void main(String[] args) throws SQLException {

        DataSource dataSource = new DataSource();

//        dataSource.createViewForPlantInfo();
//        dataSource.createViewForFullEventInfo();

        EventsService eventsService = new EventsService(dataSource);
        eventsService.addRecurringEvent("Ocimum Basilicum", Event.EventType.WATERING, LocalDate.now().plusDays(5));

        EventsService eventsService1 = new EventsService(dataSource);
        eventsService1.addRecurringEvent("Sedum Makinoi", Event.EventType.WATERING, LocalDate.now().minusDays(3));

    }

}
