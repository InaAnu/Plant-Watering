package com.ina.plantcalendar.model;

import com.fasterxml.jackson.databind.DatabindException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;


public class Events {

    private ArrayList<Event> events = new ArrayList<>();
    private DataSource dataSource = new DataSource();

    public ArrayList<Event> getUpcomingEvents (int amount) {
        ArrayList<Event> upcomingEvents = new ArrayList<>();
        if(events.size() < amount){
            amount = events.size();
        }
        for(int i=0; i<amount; i++) {
            upcomingEvents.add(events.get(i));
        }
        return upcomingEvents;
    }

    public void addEvent(String plantScientificName, Event.EventType eventType, LocalDate lastWateredOn) {

        Plant plant = dataSource.queryPlantByExactScientificName(plantScientificName); // TODO I need to figure out what will happen if there are several plants under the name;
        // TODO if such event already exists, do not add a new event
        Event event = new Event(plant, eventType, lastWateredOn);
        events.add(event);
    }

    public void addEvent(Plant plant, Event.EventType eventType, LocalDate lastWateredOn) {
        // TODO if such event already exists, do not add a new event
        Event event = new Event(plant, eventType, lastWateredOn);
        events.add(event);
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
