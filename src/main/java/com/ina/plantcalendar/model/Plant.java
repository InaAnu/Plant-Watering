package com.ina.plantcalendar.model;

import com.ina.plantcalendar.database.RecurringEvent;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "plant_information")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id", nullable=false, columnDefinition = "serial")
    private int id;
    @Column(name = "scientific_name", nullable=false)
    private String scientificName;
    @Column(name = "alias", nullable=false)
    private String alias;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable=false)
    private PlantType type;
    @Column(name = "watering_recurrence", nullable=false)
    private int wateringRecurrence;
    @Column(name = "watering_pattern", nullable=false)
    private String wateringPatternText;

    public enum PlantType {
        FERN, SMALL_TREE, CACTUS, SUCCULENT, VINE, ORCHID, OTHER, NO_IDEA, CALATHEA, MARANTA, ARUM, HERB, CLIMBER
    }

    public Plant() {
    }

    public Plant(String scientificName, PlantType type, int wateringRecurrence) {
        this.scientificName = scientificName;
        this.type = type;
        // If alias is not entered, scientific name should be used instead whenever alias is required.
        this.alias = scientificName;
        this.wateringRecurrence = wateringRecurrence;
        this.wateringPatternText = getPatternText(wateringRecurrence);
    }

    public Plant(String scientificName, String alias, PlantType type, int wateringRecurrence) {
        this.scientificName = scientificName;
        this.alias = alias;
        this.type = type;
        this.wateringRecurrence = wateringRecurrence;
        this.wateringPatternText = getPatternText(wateringRecurrence);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public PlantType getType() {
        return type;
    }

    public void setType(PlantType type) {
        this.type = type;
    }

    public int getWateringRecurrence() {
        return wateringRecurrence;
    }

    public void setWateringRecurrence(int wateringRecurrence) {
        setWateringPatternText(wateringRecurrence);
        this.wateringRecurrence = wateringRecurrence;
    }

    public String getWateringPatternText() {
        return this.wateringPatternText;
    }

    public void setWateringPatternText(int wateringRecurrence) {
        this.wateringPatternText = getPatternText(wateringRecurrence);
    }

    public String getPatternText(int patternNumber) {
        String patternText;
        if(patternNumber == 1) {
            patternText = "1x" + patternNumber + "DAY";
        } else if(patternNumber < 7 || patternNumber > 7 && patternNumber < 14) {
            patternText = "1x" + patternNumber + "DAYS";
        } else if(patternNumber == 7) {
            patternText = "1xWEEK";
        } else if(patternNumber < 28){
            int numberOfWeeks = Math.round(patternNumber/7);
            patternText = "1x" + numberOfWeeks + "WEEKS";
        } else {
            patternText = "1xMONTH";
        }
        return patternText;
    }
}
