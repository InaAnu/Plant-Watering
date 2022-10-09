package com.ina.plantcalendar.controller;

import com.ina.plantcalendar.services.EventsService;
import com.ina.plantcalendar.services.FooterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
public class ContactController {

    private final EventsService eventsService;
    private final FooterService footerService;

    public ContactController(EventsService eventsService, FooterService footerService) {
        this.eventsService = eventsService;
        this.footerService = footerService;
    }

    @RequestMapping(value={"/contact"})
    public String displayContact(Model model) throws SQLException {

        footerService.fillFooterData(model);

        return "contact.html";
    }
}
