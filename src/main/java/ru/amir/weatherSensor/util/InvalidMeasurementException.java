package ru.amir.weatherSensor.util;

public class InvalidMeasurementException extends RuntimeException{
    public InvalidMeasurementException(String message) {
        super(message);
    }
}
