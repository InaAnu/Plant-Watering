package com.ina.plantcalendar.model;

import com.fasterxml.jackson.databind.DatabindException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

@Component
public class Events {

    private ArrayList<Event> events = new ArrayList<>();
    private final DataSource dataSource;

    @Autowired
    public Events(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
    }

    // This should work for events and not aggregated lists of events
    public ArrayList<Event> getUpcomingEvents (int amount) {
        ArrayList<Event> upcomingEvents = new ArrayList<>();
        if (events.size() < amount){
            amount = events.size();
        }
        for(int i=0; i<amount; i++) {
            upcomingEvents.add(events.get(i));
        }
        return upcomingEvents;
    }

    // TODO I need to figure out what will happen if there are several plants under the name;
    public void addEvent(String plantScientificName, Event.EventType eventType, LocalDate lastWateredOn) throws SQLException {
        Plant plant = dataSource.queryPlantByExactScientificName(plantScientificName);

        if (plant == null) {
            System.out.println("Plant not in the DB.");
            return;
        }

        if (dataSource.isEventInDB(plantScientificName, eventType)) {
            return;
        } else {
            Event event = new Event(plant, eventType, lastWateredOn);
            dataSource.addEvent(dataSource.queryPlantIdByScientificName(plant.getScientificName()), eventType, lastWateredOn, event.getNextOccurrence());
        }
    }



    // TODO Get rid of this and connect everything to DB
    public void addEvent(Plant plant, Event.EventType eventType, LocalDate lastWateredOn) {
        Event event = new Event(plant, eventType, lastWateredOn);
        events.add(event);
    }

    public ArrayList<AggregatedEventsPerDay> getUpcomingAggregatedEventsForTheUpcomingWeek() {
        ArrayList<AggregatedEventsPerDay> upcomingEvents = new ArrayList<>();

        LocalDate today = LocalDate.now();
        for(int i=0; i<7; i++) {
            LocalDate date = today.plusDays(i);
            AggregatedEventsPerDay events = new AggregatedEventsPerDay(date, Event.EventType.WATERING);

            // TODO Take this information out of the database
            ArrayList<String> plantsForTheEvent = new ArrayList<>();
            plantsForTheEvent.add("Basil");
            plantsForTheEvent.add("Calathea");
            events.setPlantsForTheEvent(plantsForTheEvent);
        }
        return upcomingEvents;
    }

//    // TODO This function currently DOES NOT WORK - make it work
//    public ArrayList<Event> getEventsForOneDay(LocalDate dayOfTheEvent) {
//
//        StringBuilder plantsForTheEvent = new StringBuilder();
//        for (String plantName:plantNames) {
//            if(plantsForTheEvent.length() != 0) {
//                plantsForTheEvent.append(", ");
//            }
//            plantsForTheEvent.append(plantName);
//        }
//        String dayOfTheWeek = nextOccurrence.getDayOfWeek().toString();
//        String dateDayMonth = nextOccurrence.getDayOfMonth() + " " + nextOccurrence.getMonth().toString().substring(0,1) + nextOccurrence.getMonth().toString().substring(1).toLowerCase();
//        return dayOfTheWeek.toString().substring(0,1) + dayOfTheWeek.toString().substring(1).toLowerCase() + ", " + dateDayMonth + " | " + eventType.toString().substring(0,1) + eventType.toString().substring(1).toLowerCase() + ": " + plantsForTheEvent;
//    }
    // TODO Can I do this in a simpler way to get the WATERING and JUNE to be Watering and June?
}
