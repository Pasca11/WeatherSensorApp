package ru.amir.weatherSensor.util;

public class SensorNotFoundException extends RuntimeException{
    public SensorNotFoundException(String mes) {
        super(mes);
    }
}
