package com.ina.plantcalendar.database;

import com.ina.plantcalendar.model.Plant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PlantRepository extends CrudRepository<Plant, Integer> {

}
