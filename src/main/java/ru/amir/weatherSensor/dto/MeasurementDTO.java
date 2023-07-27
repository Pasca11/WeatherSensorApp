package ru.amir.weatherSensor.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {
    @Min(value = -100, message = "Temperature is too low")
    @Max(value = 100, message = "Temperature is to high")
    private int value;

    public MeasurementDTO() {
    }

    @NotEmpty
    private boolean raining;

    @NotNull
    private SensorDTO sensor;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
