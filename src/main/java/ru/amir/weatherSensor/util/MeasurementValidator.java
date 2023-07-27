package ru.amir.weatherSensor.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.amir.weatherSensor.dto.MeasurementDTO;
import ru.amir.weatherSensor.dto.SensorDTO;
import ru.amir.weatherSensor.models.Sensor;
import ru.amir.weatherSensor.services.SensorsService;

import java.util.Optional;
@Component
public class MeasurementValidator implements Validator {
    private final SensorsService sensorsService;

    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(MeasurementDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurement = (MeasurementDTO) target;
        Optional<Sensor> sensor = sensorsService.findByName(measurement.getSensor().getName());
        if (sensor.isEmpty()) {
            errors.rejectValue("sensor", "", "No sensor with name " + measurement.getSensor().getName());
        }
    }
}
