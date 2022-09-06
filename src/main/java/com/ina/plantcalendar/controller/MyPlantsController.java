package com.ina.plantcalendar.controller;

import com.ina.plantcalendar.model.DataSource;
import com.ina.plantcalendar.model.Plant;
import com.ina.plantcalendar.services.FooterService;
import com.ina.plantcalendar.services.MyPlantsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Slf4j
@Controller
public class MyPlantsController {

    private final MyPlantsService myPlantsService;

    @Autowired
    public MyPlantsController(MyPlantsService myPlantsService) {
        this.myPlantsService = myPlantsService;
    }

    @RequestMapping(value={"/myplants"})
    public String displayMyPlantsPage(@RequestParam(required = false, name = "add_plant") boolean addPlant, Model model) {
        model.addAttribute("add_plant", addPlant);
        model.addAttribute("plant", new Plant("Calathea Lancifolia", "Rattlesnake plant", Plant.PlantType.CALATHEA, 7));

        // Plants in the gallery
        DataSource dataSource = new DataSource();
        dataSource.open();
        for (int i=0; i<6; i++) {
            model.addAttribute("plant" + (i+1) + "_name", dataSource.queryPlants().get(i).getScientificName());
            model.addAttribute("plant" + (i+1) + "_alias", dataSource.queryPlants().get(i).getAlias());
            model.addAttribute("plant" + (i+1) + "_watering_pattern", dataSource.queryPlants().get(i).getWateringPatternText());
        }
        dataSource.close();

        FooterService footerService = new FooterService();
        footerService.fillFooterData(model);

        return "my-plants.html";
    }

    @PostMapping(value={"/savePlant"})
    public String savePlant(@Valid @ModelAttribute("plant") Plant plant, Errors errors) {
        if (errors.hasErrors()) {
            log.error("From validation failed due to: " + errors.toString());
            return "my-plants.html";
        }
        myPlantsService.savePlant(plant);
        return "redirect:/myplants";
    }
}
