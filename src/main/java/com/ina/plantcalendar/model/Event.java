package com.ina.plantcalendar.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class Event {

    private Plant plant;
    private EventType eventType;
    private LocalDate lastWateredOn;
    private LocalDate nextOccurrence;
//    public LocalDate today = LocalDate.now();
    private ArrayList<Plant> plants = new ArrayList<>();

    public enum EventType {
        WATERING, FERTILIZING, REPLANTING
    }

    public Event(Plant plant, EventType eventType, LocalDate lastWateredOn) {
        this.plant = plant;
        this.eventType = eventType;
        int wateringRecurrence = plant.getWateringRecurrence();
        this.nextOccurrence = getNextOccurrence(lastWateredOn,wateringRecurrence);
    }

    // TODO Change to abstract class, to change behaviour depending on what type of event we have.

    public LocalDate getNextOccurrence(LocalDate lastWateredOn, int wateringRecurrence) {
        return lastWateredOn.plusDays(wateringRecurrence);
    }

    public void setNextOccurrence(LocalDate nextOccurrence) {
        this.nextOccurrence = nextOccurrence;
    }

    public boolean addPlant(String scientificName, String alias, Plant.PlantType type, int wateringPattern) {
        if(findPlantByName(scientificName) == null) {
            this.plants.add(new Plant(scientificName, alias, type, wateringPattern));
            return true;
        } else {
            return false;
        }
    }

    public boolean addPlant(String scientificName, Plant.PlantType type, int wateringPatternNumber) {
        if(findPlantByScientificName(scientificName) == null) {
            this.plants.add(new Plant(scientificName, type, wateringPatternNumber));
            return true;
        } else {
            return false;
        }
    }

    public Plant findPlantByScientificName(String scientificName) {
        for(Plant plant: plants) {
            if(plant.getScientificName().equals(scientificName)){
                return plant;
            }
        }
        return null;
    }

    public ArrayList<Plant> findPlantByName(String name) {
        ArrayList<Plant> matchingPlants = new ArrayList<>();
        for(Plant plant: plants) {
            if(plant.getScientificName().equals(name) || plant.getAlias().equals(name)){
                matchingPlants.add(plant);
            }
        }
        return matchingPlants;
    }

    @Override
    public String toString() {
        String dayOfTheWeek = nextOccurrence.getDayOfWeek().toString();
        String dateDayMonth = nextOccurrence.getDayOfMonth() + " " + nextOccurrence.getMonth().toString().substring(0,1) + nextOccurrence.getMonth().toString().substring(1).toLowerCase();
        return dayOfTheWeek.substring(0,1) + dayOfTheWeek.substring(1).toLowerCase() + ", " + dateDayMonth + " | " + eventType.toString().substring(0,1) + eventType.toString().substring(1).toLowerCase() + ": " + plant.getAlias();
    }

//    Previous pattern for displaying events
//    @Override
//    public String toString() {
//        StringBuilder plantsForTheEvent = new StringBuilder();
//        for (String plantName:plantNames) {
//            if(plantsForTheEvent.length() != 0) {
//                plantsForTheEvent.append(", ");
//            }
//            plantsForTheEvent.append(plantName);
//        }
//        String dayOfTheWeek = nextOccurrence.getDayOfWeek().toString();
//        String dateDayMonth = nextOccurrence.getDayOfMonth() + " " + nextOccurrence.getMonth().toString().substring(0,1) + nextOccurrence.getMonth().toString().substring(1).toLowerCase();
//        return dayOfTheWeek.toString().substring(0,1) + dayOfTheWeek.toString().substring(1).toLowerCase() + ", " + dateDayMonth + " | " + eventType.toString().substring(0,1) + eventType.toString().substring(1).toLowerCase() + ": " + plantsForTheEvent;
//    }
    // TODO Can I do this in a simpler way to get the WATERING and JUNE to be Watering and June?
}
