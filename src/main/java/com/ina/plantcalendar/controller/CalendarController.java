package com.ina.plantcalendar.controller;

//import com.ina.plantcalendar.model.Event;
//import com.ina.plantcalendar.model.Events;
import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Events;
import com.ina.plantcalendar.model.Plant;
import com.ina.plantcalendar.services.FooterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class CalendarController {

    @RequestMapping(value = "/calendar")
    public String displayCalendar(Model model, @RequestParam(required = false, name = "upcoming_events") ArrayList<Event> upcomingEvents) throws SQLException {


//        TODO Change this section to use the code from the database or from the Calendar Service

        Plant calathea = new Plant("Calathea Lancifolia", "Rattlesnake Plant", Plant.PlantType.CALATHEA, 7);
        Plant maranta = new Plant("Maranta Leuconeura", "Fascinator Tricolor", Plant.PlantType.MARANTA, 7);
        Plant goldenPhotos = new Plant("Epipremnum Aureum", "Golden Pothos", Plant.PlantType.ARUM, 7);
        Plant basil = new Plant("Ocium Basilicum", "Basil", Plant.PlantType.HERB, 1);
        Plant veronaVein = new Plant("Parthenocissum Striata", "Verona Vein", Plant.PlantType.CLIMBER, 7);
        Plant tundraTornado = new Plant("Sedum Makinoi", "Tundra Tornado", Plant.PlantType.SUCCULENT, 14);

        Events events = new Events();
        events.addEvent(calathea, Event.EventType.WATERING, LocalDate.now().minusDays(6));
        events.addEvent(maranta, Event.EventType.WATERING, LocalDate.now().minusDays(5));
        events.addEvent(tundraTornado, Event.EventType.WATERING, LocalDate.now().minusDays(7));
        events.addEvent(basil, Event.EventType.WATERING, LocalDate.now().minusDays(1));
        events.addEvent(veronaVein, Event.EventType.WATERING, LocalDate.now().minusDays(3));
        events.addEvent(goldenPhotos, Event.EventType.WATERING,LocalDate.now().minusDays(4));

        upcomingEvents = events.getUpcomingEvents(6);

        model.addAttribute("upcoming_events", upcomingEvents);

        FooterService footerService = new FooterService();
        footerService.fillFooterData(model);
        return "calendar.html";
    }
}
