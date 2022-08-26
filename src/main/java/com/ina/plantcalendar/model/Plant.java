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
    private String wateringPattern;
    // TODO check out how to add this information as a drop down menu
    public enum PlantType {
        FERN, SMALL_TREE, CACTUS, SUCCULENT, VINE, ORCHID, OTHER, NO_IDEA, CALATHEA, MARANTA, ARUM, HERB, CLIMBER
    }

    public Plant(String scientificName, PlantType type, String wateringPattern) {
        this.scientificName = scientificName;
        this.type = type;
//        this.additionalInfo = additionalInfo;
        this.wateringPattern = wateringPattern;
    }

    public Plant(String scientificName, String alias, PlantType type, String wateringPattern) {
        this.scientificName = scientificName;
        this.alias = alias;
        this.type = type;
//        this.additionalInfo = additionalInfo;
        this.wateringPattern = wateringPattern;
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

    public String getWateringPattern() {
        return wateringPattern;
    }

    public void setWateringPattern(String wateringPattern) {
        this.wateringPattern = wateringPattern;
    }
}
