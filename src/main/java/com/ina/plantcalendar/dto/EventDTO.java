package com.ina.plantcalendar.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class EventDTO {

    @NotBlank(message = "Plant must not be blank")
    private String plantName;
    @NotBlank(message = "Type must not be blank")
    private String type;
    @NotNull(message = "Date must not be blank")
    @PastOrPresent(message = "Date must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastWateredOn;

    public EventDTO() {
    }

    public EventDTO(String plantScientificName, String eventType, String lastWateredOn) {
        this.plantName = plantScientificName;
        this.type = eventType;
        this.lastWateredOn = LocalDate.parse(lastWateredOn);
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getLastWateredOn() {
        return lastWateredOn;
    }

    public void setLastWateredOn(LocalDate lastWateredOn) {
        this.lastWateredOn = lastWateredOn;
    }
}
