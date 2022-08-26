package com.ina.plantcalendar.model;

import com.fasterxml.jackson.databind.DatabindException;

import java.time.LocalDate;
import java.util.ArrayList;


public class Events {

    private ArrayList<Event> events = new ArrayList<>();
    public LocalDate today = LocalDate.now();

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

    public void addEvent(Event event) {
        events.add(event);
    }

}
