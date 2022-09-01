package com.ina.plantcalendar.controller;

//import com.ina.plantcalendar.model.Event;
//import com.ina.plantcalendar.model.Events;
import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Events;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

@Controller
public class CalendarController {

    @RequestMapping(value = "/calendar")
    public String displayCalendar(Model model, @RequestParam(required = false, name = "upcoming_events") ArrayList<Event> upcomingEvents) {


//        TODO Change this section to use the code from the database

        ArrayList<String> plantNames1 = new ArrayList<>();
        plantNames1.add("Rattlesnake Plant");
        plantNames1.add("Basil");
        plantNames1.add("Fascinator Tricolor");
        Event event1 = new Event(plantNames1, Event.EventType.WATERING, LocalDate.now().minus(Period.ofDays(7)));

        ArrayList<String> plantNames2 = new ArrayList<>();
        plantNames2.add("Basil");
        Event event2 = new Event(plantNames2, Event.EventType.WATERING, LocalDate.now().minus(Period.ofDays(6)));

        ArrayList<String> plantNames3 = new ArrayList<>();
        plantNames3.add("Basil");
        plantNames3.add("Golden Pothos");
        plantNames3.add("Verona Vein");
        Event event3 = new Event(plantNames3, Event.EventType.WATERING, LocalDate.now().minus(Period.ofDays(5)));

        ArrayList<String> plantNames4 = new ArrayList<>();
        plantNames4.add("Basil");
        plantNames4.add("Tundra Tornado");
        Event event4 = new Event(plantNames4, Event.EventType.WATERING, LocalDate.now().minus(Period.ofDays(4)));

        ArrayList<String> plantNames5 = new ArrayList<>();
        plantNames5.add("Basil");
        Event event5 = new Event(plantNames5, Event.EventType.WATERING, LocalDate.now().minus(Period.ofDays(3)));

        Events events = new Events();
        events.addEvent(event1);
        events.addEvent(event2);
        events.addEvent(event3);
        events.addEvent(event4);
        events.addEvent(event5);

        upcomingEvents = events.getUpcomingEvents(5);

        model.addAttribute("upcoming_events", upcomingEvents);
        return "calendar.html";
    }
}
