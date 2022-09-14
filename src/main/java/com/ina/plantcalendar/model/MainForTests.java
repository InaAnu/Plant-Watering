package com.ina.plantcalendar.model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class MainForTests {
    public static void main(String[] args) throws SQLException {

//        DataSource dataSource = new DataSource();
//
////        if(!dataSource.open()) {
////            System.out.println("Can't open datasource");
////            return;
////        }
//        dataSource.open();
////        dataSource.test();
////
//        dataSource.createViewForPlantInfo();
//        dataSource.createViewForFullEventInfo();
//
////        List<Plant> plantsInfoByAlias = dataSource.queryPlantByName("au");
////        if(plantsInfoByAlias == null) {
////            System.out.println("No such plants!");
////            return;
////        } else {
////            for (Plant plant : plantsInfoByAlias) {
////                System.out.println("Scientific name: " + plant.getScientificName() + "\nAlias: " + plant.getAlias() + "\nType: " + plant.getType() + "\nWatering pattern: " + plant.getWateringPattern());
////            }
////        }
//
//        List<Plant> plants = dataSource.queryPlants();
//        if(plants == null) {
//            System.out.println("No plants available!");
//            return;
//        } else {
//            for (Plant plant : plants) {
//                System.out.println("Scientific name: " + plant.getScientificName() + " Alias: " + plant.getAlias() + " Type: " + plant.getType() + " Watering pattern: " + plant.getWateringPatternText());
//            }
//        }
//
//        System.out.println("-----");
//        System.out.println(dataSource.queryPlantByExactScientificName("Calathea Lancifolia"));
//
//        boolean isEventInDB = dataSource.isEventInDB("Calathea Lancifolia", Event.EventType.WATERING);
//        if(isEventInDB) {
//            System.out.println("Event is in DB");
//        } else {
//            System.out.println("The event was not found");
//        }

        Events events = new Events();
        events.addEvent("Maranta Leuconeura", Event.EventType.WATERING, LocalDate.now());

        Events events1 = new Events();
        events1.addEvent("Ocimum Basilicum", Event.EventType.WATERING, LocalDate.now());

//        System.out.println(dataSource.queryPlants().get(0).getScientificName());

//        ArrayList<String> plantNames1 = new ArrayList<>();
//        plantNames1.add("Rattlesnake Plant");
//        plantNames1.add("Basil");
//        plantNames1.add("Fascinator Tricolor");
//        Event event1 = new Event(plantNames1, Event.EventType.WATERING, LocalDate.now());
//
//        ArrayList<String> plantNames2 = new ArrayList<>();
//        plantNames2.add("Basil");
//        Event event2 = new Event(plantNames2, Event.EventType.WATERING, LocalDate.now().plus(Period.ofDays(1)));
//
//        ArrayList<String> plantNames3 = new ArrayList<>();
//        plantNames3.add("Basil");
//        plantNames3.add("Golden Pothos");
//        plantNames3.add("Verona Vein");
//        Event event3 = new Event(plantNames3, Event.EventType.WATERING, LocalDate.now().plus(Period.ofDays(2)));
//
//        ArrayList<String> plantNames4 = new ArrayList<>();
//        plantNames4.add("Basil");
//        plantNames4.add("Tundra Tornado");
//        Event event4 = new Event(plantNames4, Event.EventType.WATERING, LocalDate.now().plus(Period.ofDays(3)));
//
//        ArrayList<String> plantNames5 = new ArrayList<>();
//        plantNames5.add("Basil");
//        Event event5 = new Event(plantNames5, Event.EventType.WATERING, LocalDate.now().plus(Period.ofDays(4)));
//
//        Events events = new Events();
//        events.addEvent(event1);
//        events.addEvent(event2);
//        events.addEvent(event3);
//        events.addEvent(event4);
//        events.addEvent(event5);
//
//        ArrayList<Event> eventsList = events.getUpcomingEvents(5);

//        for (var currentEvent:eventsList) {
//            System.out.println(currentEvent);
//        }

//        System.out.println(event1.getDate());

    }

}
