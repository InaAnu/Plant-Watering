package com.ina.plantcalendar.controller;

import com.ina.plantcalendar.model.Events;
import com.ina.plantcalendar.services.FooterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class ContactController {

    private final Events events;
    private final FooterService footerService;

    public ContactController(Events events, FooterService footerService) {
        this.events = events;
        this.footerService = footerService;
    }

    @RequestMapping(value={"/contact"})
    public String displayContact(Model model) throws SQLException {

        footerService.fillFooterData(model);

        return "contact.html";
    }
}
