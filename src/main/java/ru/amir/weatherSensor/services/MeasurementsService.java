package ru.amir.weatherSensor.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.amir.weatherSensor.models.Measurement;
import ru.amir.weatherSensor.models.Sensor;
import ru.amir.weatherSensor.repositories.MeasurementsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;

    public MeasurementsService(MeasurementsRepository measurementsRepository) {
        this.measurementsRepository = measurementsRepository;
    }

    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }

    public Optional<Measurement> findById(int id) {
        return measurementsRepository.findById(id);
    }


    @Transactional
    public void save(Measurement measurement, Sensor sensor) {
        measurement.setSensor(sensor);
        measurement.setCreatedAt(LocalDateTime.now());
        measurementsRepository.save(measurement);
    }
}
