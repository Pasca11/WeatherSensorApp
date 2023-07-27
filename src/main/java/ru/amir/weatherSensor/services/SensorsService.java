package ru.amir.weatherSensor.services;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.amir.weatherSensor.models.Measurement;
import ru.amir.weatherSensor.models.Sensor;
import ru.amir.weatherSensor.repositories.SensorsRepository;
import ru.amir.weatherSensor.util.SensorNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;

    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }

    public Optional<Sensor> findOne(int id) {
        return sensorsRepository.findById(id);
    }

    public Optional<Sensor> findByName(String name) {
        return sensorsRepository.findByName(name);
    }

    public List<Measurement> getAllMeasurementsById(int id) {
        Optional<Sensor> sensor = findOne(id);
        if (sensor.isEmpty())
            throw new SensorNotFoundException("No such user with id:" + id);
        Hibernate.initialize(sensor.get().getMeasurements());
        return sensor.get().getMeasurements();
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }

}
