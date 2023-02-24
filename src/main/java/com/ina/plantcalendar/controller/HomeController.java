package com.ina.plantcalendar.controller;

import com.ina.plantcalendar.services.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private final FooterService footerService;

    @Autowired
    public HomeController(FooterService footerService) {
        this.footerService = footerService;
    }

    @RequestMapping(value={"/","/home",""})
    public String displayHomePage(Model model) {

        footerService.fillFooterData(model);

        return "home.html";
    }
}
