package com.ina.plantcalendar.services;

import com.ina.plantcalendar.model.Plant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyPlantsService {

    public boolean savePlant(Plant plant) {
        boolean isSaved = true;
        // TODO Need to put the data in a database
        log.info(plant.toString());
        return isSaved;
    }
}
