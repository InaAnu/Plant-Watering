package com.ina.plantcalendar.dto;

import com.ina.plantcalendar.model.Plant;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PlantDTO {

    @NotBlank(message = "Name must not be blank")
    @Size(min=3, message = "Name must be at least 3 characters long")
    private String scientificName;
    @NotBlank(message = "Your plants does not have a name?! Just call it \"The Plant in the Kitchen\"")
    private String alias;
    @NotBlank(message = "Type must not be blank")
    private com.ina.plantcalendar.model.Plant.PlantType type;
        private String additionalInfo;
    @Size(min=1, max=31, message = "Number of days must be between 1 and 31")
    private int wateringRecurrence;
    private String wateringPatternText;
    // TODO check out how to add this information as a drop down menu
    public Plant.PlantType plantType;

    public PlantDTO(String scientificName, com.ina.plantcalendar.model.Plant.PlantType type, int wateringRecurrence) {
        this.scientificName = scientificName;
        this.type = type;
        this.additionalInfo = additionalInfo;
        // If alias is not entered, scientific name should be used instead whenever alias is required.
        this.alias = scientificName;
        this.wateringRecurrence = wateringRecurrence;
        this.wateringPatternText = getPatternText(wateringRecurrence);
    }

    public PlantDTO(String scientificName, String alias, com.ina.plantcalendar.model.Plant.PlantType type, int wateringRecurrence) {
        this.scientificName = scientificName;
        this.alias = alias;
        this.type = type;
//        this.additionalInfo = additionalInfo;
        this.wateringRecurrence = wateringRecurrence;
        this.wateringPatternText = getPatternText(wateringRecurrence);
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

    public com.ina.plantcalendar.model.Plant.PlantType getType() {
        return type;
    }

    public void setType(com.ina.plantcalendar.model.Plant.PlantType type) {
        this.type = type;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
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

