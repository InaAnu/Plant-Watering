package com.ina.plantcalendar.controller;

//import com.ina.plantcalendar.model.Event;
//import com.ina.plantcalendar.services.EventsService;
import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.services.EventsService;
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

    private final EventsService eventsService;
    private final FooterService footerService;

    public CalendarController(EventsService eventsService, FooterService footerService){
        this.eventsService = eventsService;
        this.footerService = footerService;
    }

    @RequestMapping(value = "/calendar")
    public String displayCalendar(Model model) throws SQLException {

        footerService.fillFooterData(model);

        return "calendar.html";
    }
}
