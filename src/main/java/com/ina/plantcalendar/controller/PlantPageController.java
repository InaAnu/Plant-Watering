package com.ina.plantcalendar.controller;

import com.ina.plantcalendar.database.MyDataSource;
import com.ina.plantcalendar.database.IMyDataSource;
import com.ina.plantcalendar.model.Plant;
import com.ina.plantcalendar.services.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Controller
public class PlantPageController {

    private final IMyDataSource dataSource;
    private final FooterService footerService;

    @Autowired
    public PlantPageController(MyDataSource dataSource, FooterService footerService) {
        this.dataSource = dataSource;
        this.footerService = footerService;
    }

    @GetMapping(value={"/plant-page/{plantScientificName}"})
    public String displayPlantPage(Model model, @PathVariable String plantScientificName) throws SQLException {

        Plant plant = dataSource.queryPlantByExactScientificName(plantScientificName);
        model.addAttribute("scientific_name", plant.getScientificName());
        model.addAttribute("alias", plant.getAlias());
        model.addAttribute("plant_type", plant.getType());
        model.addAttribute("watering_pattern", plant.getWateringPatternText());
        model.addAttribute("upcoming_plant_events", dataSource.findAllEventsForAPlantByDate(LocalDate.now(), LocalDate.now().plusMonths(1), plant.getScientificName())
                .stream()
                .map(event -> new StringBuilder(
                        StringUtils.capitalize(event.getEventDate().getDayOfWeek().toString().toLowerCase()) + ", " +
                        event.getEventDate().getDayOfMonth() + " " +
                        StringUtils.capitalize(event.getEventDate().getMonth().toString().toLowerCase()) + " | " +
                         StringUtils.capitalize(event.getEventType().toString().toLowerCase())))
                .collect(Collectors.toList()));

        footerService.fillFooterData(model);

        return "plant-page.html";
    }
}
