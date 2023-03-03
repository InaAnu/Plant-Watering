package com.ina.plantcalendar.controller;

import com.ina.plantcalendar.database.MyDataSource;
import com.ina.plantcalendar.database.IMyDataSource;
import com.ina.plantcalendar.dto.EventDTO;
import com.ina.plantcalendar.dto.PlantDTO;
import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;
import com.ina.plantcalendar.services.EventsService;
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

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Controller
public class MyPlantsController {

    private final MyPlantsService myPlantsService;
    private final EventsService eventsService;
    private final IMyDataSource dataSource;
    private final FooterService footerService;

    @Autowired
    public MyPlantsController(MyPlantsService myPlantsService, EventsService eventsService, MyDataSource dataSource, FooterService footerService) {
        this.myPlantsService = myPlantsService;
        this.eventsService = eventsService;
        this.dataSource = dataSource;
        this.footerService = footerService;
    }

    @RequestMapping(value={"/myplants"})
    public String displayMyPlantsPage(Model model) throws SQLException {
        displayGallery(model);

        footerService.fillFooterData(model);

        return "my-plants.html";
    }

    @RequestMapping(value={"/savePlant"})
    public String displayAddPlantForm(Model model) throws SQLException {
        model.addAttribute("add_plant", true);
        model.addAttribute("plant", new PlantDTO());
        model.addAttribute("plant_types", Plant.PlantType.values());

        displayGallery(model);
        footerService.fillFooterData(model);

        return "my-plants.html";
    }

    @PostMapping(value={"/savePlant"})
    public String savePlant(@Valid @ModelAttribute("plant") PlantDTO plant, Errors errors, Model model) throws SQLException {
        model.addAttribute("add_plant", true);
        model.addAttribute("plant_types", Plant.PlantType.values());

        displayGallery(model);
        footerService.fillFooterData(model);

        if (errors.hasErrors()) {
            log.error("From validation failed due to: " + errors.getAllErrors());
            return "my-plants.html";
        }
        if (!myPlantsService.savePlant(plant)) {
            model.addAttribute(("error_plant_already_exists"), true);
            return "my-plants.html";
        }

        return "redirect:/myplants";
    }

    @RequestMapping(value={"/saveEvent"})
    public String displayAddEventForm(Model model) throws SQLException {
        model.addAttribute("add_event", true);
        model.addAttribute("event", new EventDTO());
        model.addAttribute("event_types", Event.EventType.values());

        displayGallery(model);
        footerService.fillFooterData(model);

        return "my-plants.html";
    }

    @PostMapping(value={"/saveEvent"})
    public String saveEvent(@Valid @ModelAttribute("event") EventDTO event, Errors errors, Model model) throws SQLException {
        model.addAttribute("add_event", true);
        model.addAttribute("event_types", Event.EventType.values());

        displayGallery(model);
        footerService.fillFooterData(model);

        if (errors.hasErrors()) {
            log.error("Form validation failed due to: " + errors.getAllErrors());
            return "my-plants.html";
        }
        if(dataSource.queryPlantByExactScientificNameOrExactAlias(event.getPlantName()) == null) {
            model.addAttribute("error_plant_does_not_exist", true);
            return "my-plants.html";
        }
        if (!eventsService.saveRecurringEvent(event)) {
            model.addAttribute("error_event_already_exists", true);
            return "my-plants.html";
        }
        return "redirect:/myplants";
    }

    private void displayGallery(Model model) throws SQLException {
        List<Plant> plants = dataSource.queryPlants();
        for (int i=0; i< plants.size(); i++) {
            model.addAttribute("plant" + (i+1) + "_scientific_name", plants.get(i).getScientificName());
            model.addAttribute("plant" + (i+1) + "_alias", plants.get(i).getAlias());
            model.addAttribute("plant" + (i+1) + "_watering_pattern", plants.get(i).getWateringPatternText());
        }
    }
}
