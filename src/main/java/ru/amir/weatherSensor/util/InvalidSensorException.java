package ru.amir.weatherSensor.util;

public class InvalidSensorException extends RuntimeException {
    public InvalidSensorException(String mes) {
        super(mes);
    }
}
