package com.ina.plantcalendar.services;

import com.ina.plantcalendar.database.IMyDataSource;
import com.ina.plantcalendar.dto.PlantDTO;
import com.ina.plantcalendar.model.Plant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Slf4j
public class MyPlantsService {

    private final IMyDataSource dataSource;

    @Autowired
    public MyPlantsService(IMyDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean savePlant(PlantDTO plantDTO) throws SQLException {
        boolean isSaved = false;
        if (dataSource.queryPlantByExactScientificName(plantDTO.getScientificName()) == null) {
            dataSource.addPlant(plantDTO.getScientificName(), plantDTO.getAlias(), Plant.PlantType.valueOf(plantDTO.getType()), plantDTO.getWateringRecurrence());
            isSaved = true;
        }
        return isSaved;
    }
}
