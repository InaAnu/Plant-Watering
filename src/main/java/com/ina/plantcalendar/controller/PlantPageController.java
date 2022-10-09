package com.ina.plantcalendar.controller;

import com.ina.plantcalendar.services.FooterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
public class PlantPageController {

    private final FooterService footerService;

    public PlantPageController(FooterService footerService) {
        this.footerService = footerService;
    }

    @RequestMapping(value={"/plant-page"})
    public String displayPlantPage (Model model) throws SQLException {

        footerService.fillFooterData(model);

        return "plant-page.html";
    }
}
