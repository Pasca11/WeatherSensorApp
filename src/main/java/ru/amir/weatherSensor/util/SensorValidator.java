package ru.amir.weatherSensor.util;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.amir.weatherSensor.models.Sensor;
import ru.amir.weatherSensor.services.SensorsService;

import java.util.Optional;
@Component
public class SensorValidator implements Validator {
    private final SensorsService service;

    public SensorValidator(SensorsService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Sensor.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        Optional<Sensor> sensorOptional = service.findByName(sensor.getName());
        if (sensorOptional.isPresent())
            errors.rejectValue("name", "", "Sensor with such name is already exist");
    }
}
