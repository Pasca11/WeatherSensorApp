package ru.amir.weatherSensor.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "Sensor`s name shouldn`t be empty")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters long")
    private String name;

    public SensorDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
