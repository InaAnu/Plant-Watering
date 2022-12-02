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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "_id")
    private int id;
    @Column(name = "scientific_name")
    private String scientificName;
    @Column(name = "alias")
    private String alias;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PlantType type;
    @Column(name = "watering_recurrence")
    private int wateringRecurrence;
    @Column(name = "watering_pattern")
    private String wateringPatternText;
    @OneToOne(mappedBy = "plant_information")
    private RecurringEvent recurringEvent;
    public enum PlantType {
        FERN, SMALL_TREE, CACTUS, SUCCULENT, VINE, ORCHID, OTHER, NO_IDEA, CALATHEA, MARANTA, ARUM, HERB, CLIMBER
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

    public Plant() {
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
