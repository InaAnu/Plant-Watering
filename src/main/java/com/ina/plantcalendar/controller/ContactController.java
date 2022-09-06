package com.ina.plantcalendar.controller;

import com.ina.plantcalendar.services.FooterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    @RequestMapping(value={"/contact"})
    public String displayContact(Model model) {

        FooterService footerService = new FooterService();
        footerService.fillFooterData(model);

        return "contact.html";
    }
}
