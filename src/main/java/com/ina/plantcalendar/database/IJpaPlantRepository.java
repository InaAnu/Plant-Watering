package com.ina.plantcalendar.database;

import com.ina.plantcalendar.model.Plant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IJpaPlantRepository extends CrudRepository<Plant, Integer> {

    @Query("SELECT p FROM Plant p WHERE p.alias LIKE ?1 OR p.scientificName LIKE ?2")
    List<Plant> queryByName(String name);

    Plant queryByScientificName(String scientificName);

    int queryIdByScientificName(String scientificName);
}
