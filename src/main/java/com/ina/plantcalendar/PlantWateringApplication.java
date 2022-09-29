package com.ina.plantcalendar;

import com.ina.plantcalendar.model.Events;
//import com.ina.plantcalendar.services.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class PlantWateringApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlantWateringApplication.class, args);

//		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
//		Events events = context.getBean(Events.class);
	}

}
