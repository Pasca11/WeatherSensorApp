package ru.amir.weatherSensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.amir.weatherSensor.dto.SensorDTO;
import ru.amir.weatherSensor.models.Sensor;
import ru.amir.weatherSensor.services.SensorsService;
import ru.amir.weatherSensor.util.ExceptionHttpResponse;
import ru.amir.weatherSensor.util.InvalidSensorException;
import ru.amir.weatherSensor.util.SensorNotFoundException;
import ru.amir.weatherSensor.util.SensorValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorValidator sensorValidator;
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;

    public SensorsController(SensorValidator sensorValidator, SensorsService sensorsService, ModelMapper modelMapper) {
        this.sensorValidator = sensorValidator;
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Sensor> sensors() {
        return sensorsService.findAll();
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> postSensor(@RequestBody @Valid SensorDTO sensor,
                                                 BindingResult bindingResult) {
        sensorValidator.validate(modelMapper.map(sensor, Sensor.class), bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for (FieldError e : errorList)
                stringBuilder.append(e.getField())
                        .append(" - ")
                        .append(e.getDefaultMessage())
                        .append(";");
            throw new InvalidSensorException(stringBuilder.toString());
        }
        sensorsService.save(modelMapper.map(sensor, Sensor.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionHttpResponse> sensorValid(InvalidSensorException e) {
        ExceptionHttpResponse response = new ExceptionHttpResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
