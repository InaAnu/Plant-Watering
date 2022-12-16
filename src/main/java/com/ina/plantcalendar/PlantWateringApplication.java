package com.ina.plantcalendar;

import com.ina.plantcalendar.database.MyDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

@SpringBootApplication
@EnableJpaRepositories("com.ina.plantcalendar.database")
@EntityScan(basePackages = {"com.ina.plantcalendar.model", "com.ina.plantcalendar.database"})
@ComponentScan(basePackages = {"com.ina.plantcalendar.services","com.ina.plantcalendar.model", "com.ina.plantcalendar.database", "com.ina.plantcalendar.dto", "com.ina.plantcalendar.controller"})
public class PlantWateringApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlantWateringApplication.class, args);
	}

}
