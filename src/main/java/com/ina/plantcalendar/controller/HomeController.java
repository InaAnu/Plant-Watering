package com.ina.plantcalendar.controller;

import com.ina.plantcalendar.services.FooterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
public class HomeController {

    @RequestMapping(value={"/","/home",""})
    public String displayHomePage(Model model) throws SQLException {

        FooterService footerService = new FooterService();
        footerService.fillFooterData(model);

        return "home.html";
    }
}
