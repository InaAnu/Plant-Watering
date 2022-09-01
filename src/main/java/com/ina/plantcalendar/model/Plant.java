package com.ina.plantcalendar.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class Plant {

    @NotBlank(message = "Name must not be blank")
    @Size(min=3, message = "Name must be at least 3 characters long")
    private String scientificName;
    private String alias;
    @NotBlank(message = "Type must not be blank")
    private PlantType type;
//    private String additionalInfo;
    @Size(min=1, max=31, message = "Number of days must be between 1 and 31")
    private int wateringRecurrence;
    private String wateringPatternText;
    // TODO check out how to add this information as a drop down menu
    public enum PlantType {
        FERN, SMALL_TREE, CACTUS, SUCCULENT, VINE, ORCHID, OTHER, NO_IDEA, CALATHEA, MARANTA, ARUM, HERB, CLIMBER
    }

    public Plant(String scientificName, PlantType type, int wateringRecurrence) {
        this.scientificName = scientificName;
        this.type = type;
//        this.additionalInfo = additionalInfo;
        // If alias is not entered, scientific name should be used instead whenever alias is required.
        this.alias = scientificName;
        this.wateringRecurrence = wateringRecurrence;
        this.wateringPatternText = getPatternText(wateringRecurrence);
    }

    public Plant(String scientificName, String alias, PlantType type, int wateringRecurrence) {
        this.scientificName = scientificName;
        this.alias = alias;
        this.type = type;
//        this.additionalInfo = additionalInfo;
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

//    public String getAdditionalInfo() {
//        return additionalInfo;
//    }

//    public void setAdditionalInfo(String additionalInfo) {
//        this.additionalInfo = additionalInfo;
//    }

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

    // TODO change this to private and check if settingWateringRecurrence works correctly for the pattern too :D
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
