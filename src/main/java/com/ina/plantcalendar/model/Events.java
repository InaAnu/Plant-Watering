package com.ina.plantcalendar.model;

import com.fasterxml.jackson.databind.DatabindException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;


public class Events {

    private ArrayList<Event> events = new ArrayList<>();
    private DataSource dataSource = new DataSource();
    private LocalDate today = LocalDate.now();


    public Events() throws SQLException {
        dataSource.open();
    }

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

    public void addEvent(Plant plant, Event.EventType eventType, LocalDate lastWateredOn) {
        // TODO if such event already exists, do not add a new event
        // TODO I need to figure out what will happen if there are several plants under the name;
        Event event = new Event(plant, eventType, lastWateredOn);
        events.add(event);
    }

    public ArrayList<AggregatedEventsPerDay> getUpcomingAggregatedEventsForTheUpcomingWeek() {
        ArrayList<AggregatedEventsPerDay> upcomingEvents = new ArrayList<>();

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
