package com.ina.plantcalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

@SpringBootApplication
@EnableJpaRepositories("com.ina.plantcalendar.database")
@EntityScan("com.ina.plantcalendar.model")
public class PlantWateringApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlantWateringApplication.class, args);
	}

}
