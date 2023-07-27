package ru.amir.weatherSensor.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.amir.weatherSensor.dto.MeasurementDTO;
import ru.amir.weatherSensor.models.Measurement;
import ru.amir.weatherSensor.models.Sensor;
import ru.amir.weatherSensor.services.MeasurementsService;
import ru.amir.weatherSensor.services.SensorsService;
import ru.amir.weatherSensor.util.ExceptionHttpResponse;
import ru.amir.weatherSensor.util.InvalidMeasurementException;
import ru.amir.weatherSensor.util.MeasurementValidator;
import ru.amir.weatherSensor.util.SensorNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService service;
    private final SensorsService sensorsService;
    private final MeasurementValidator validator;
    private final ModelMapper modelMapper;

    public MeasurementsController(MeasurementsService service, SensorsService sensorsService, MeasurementValidator validator, ModelMapper modelMapper) {
        this.service = service;
        this.sensorsService = sensorsService;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Measurement> getAllMeasurements() {
        return service.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody MeasurementDTO measurement,
                                                     BindingResult bindingResult) {
        validator.validate(measurement, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder str = new StringBuilder();
            List<FieldError> list = bindingResult.getFieldErrors();
            for (FieldError e : list) {
                str.append(e.getField())
                        .append(" - ")
                        .append(e.getDefaultMessage())
                        .append(";");
            }
            throw new InvalidMeasurementException(str.toString());
        }
        Sensor sensor = sensorsService.findByName(measurement.getSensor().getName()).get();
        service.save(modelMapper.map(measurement, Measurement.class), sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    public ResponseEntity<ExceptionHttpResponse> sensorValid(SensorNotFoundException e) {
        ExceptionHttpResponse response = new ExceptionHttpResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionHttpResponse> sensorValid(InvalidMeasurementException e) {
        ExceptionHttpResponse response = new ExceptionHttpResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
