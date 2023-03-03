package com.ina.plantcalendar.dto;

import com.ina.plantcalendar.model.Plant;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PlantDTO {

    @NotBlank(message = "Name must not be blank")
    @Size(min=3, message = "Name must be at least 3 characters long")
    private String scientificName;
    @NotBlank(message = "Your plants does not have a name? Just call it \"The Plant in the Kitchen\"")
    private String alias;
    @NotBlank(message = "Type must not be blank")
    private String type;
    @Range(min=1, max=31, message = "Number of days must be between 1 and 31")
    private int wateringRecurrence;

    public PlantDTO() {
    }

    public PlantDTO(String scientificName, String type, int wateringRecurrence) {
        this.scientificName = scientificName;
        this.type = type;
        // If alias is not entered, scientific name should be used instead whenever alias is required.
        this.alias = scientificName;
        this.wateringRecurrence = wateringRecurrence;
    }

    public PlantDTO(String scientificName, String alias, String type, int wateringRecurrence) {
        this.scientificName = scientificName;
        this.alias = alias;
        this.type = type;
        this.wateringRecurrence = wateringRecurrence;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWateringRecurrence() {
        return wateringRecurrence;
    }

    public void setWateringRecurrence(int wateringRecurrence) {
        this.wateringRecurrence = wateringRecurrence;
    }

    @Override
    public String toString() {
        return "PlantDTO{" +
                "scientificName='" + scientificName + '\'' +
                ", alias='" + alias + '\'' +
                ", type=" + type +
                ", wateringRecurrence=" + wateringRecurrence +
                '}';
    }
}

