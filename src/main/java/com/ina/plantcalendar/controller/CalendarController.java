package com.ina.plantcalendar.controller;

//import com.ina.plantcalendar.model.Event;
//import com.ina.plantcalendar.services.EventsService;
import com.ina.plantcalendar.services.EventsService;
import com.ina.plantcalendar.services.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
public class CalendarController {

    private final EventsService eventsService;
    private final FooterService footerService;

    @Autowired
    public CalendarController(EventsService eventsService, FooterService footerService){
        this.eventsService = eventsService;
        this.footerService = footerService;
    }

    @RequestMapping(value = "/calendar")
    public String displayCalendar(Model model) {

        footerService.fillFooterData(model);

        return "calendar.html";
    }
}
