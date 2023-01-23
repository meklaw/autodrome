package ru.meklaw.autodrome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meklaw.autodrome.models.GpsPoint;
import ru.meklaw.autodrome.models.Vehicle;
import ru.meklaw.autodrome.repositories.GpsPointRepository;
import ru.meklaw.autodrome.repositories.VehiclesRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GpsPointService {
    private final GpsPointRepository gpsPointRepository;
    private final VehiclesRepository vehiclesRepository;

    @Autowired
    public GpsPointService(GpsPointRepository gpsPointRepository, VehiclesRepository vehiclesRepository) {
        this.gpsPointRepository = gpsPointRepository;
        this.vehiclesRepository = vehiclesRepository;
    }

    @Transactional(readOnly = true)
    public List<GpsPoint> findAll(long vehicleId, Optional<ZonedDateTime> start, Optional<ZonedDateTime> end) {
        if (vehicleId != -1 && start.isPresent() && end.isPresent())
            return gpsPointRepository.findAllByVehicleIdAndDateTimeBetweenOrderByDateTimeAsc(vehicleId, start.get(), end.get());

        if (vehicleId != -1 && start.isPresent())
            return gpsPointRepository.findAllByVehicleIdAndDateTimeAfterOrderByDateTimeAsc(vehicleId, start.get());

        if (vehicleId != -1 && end.isPresent())
            return gpsPointRepository.findAllByVehicleIdAndDateTimeBeforeOrderByDateTimeAsc(vehicleId, end.get());

        if (vehicleId != -1)
            return gpsPointRepository.findAllByVehicleIdOrderByDateTimeAsc(vehicleId);


        return gpsPointRepository.findAll();
    }

    public GpsPoint findById(long id) {
        return gpsPointRepository.findById(id)
                                 .orElseThrow();
    }

    @Transactional()
    public void init() {
        List<Vehicle> vehicles = vehiclesRepository.findAll();
        for (int i = 0; i < 100; i++) {
            Vehicle vehicle = vehiclesRepository.findById(vehicles.get(i)
                                                                  .getId())
                                                .orElseThrow();
            for (int j = 0; j < 15; j++) {
                var point = new GpsPoint();
                point.setVehicle(vehiclesRepository.findById(vehicles.get(i)
                                                                     .getId())
                                                   .orElseThrow());
                point.setDateTime(ZonedDateTime.now(ZoneId.of("UTC"))
                                               .minusHours(j));
                point.setX(Math.random() * 100);
                point.setY(Math.random() * 100);
                gpsPointRepository.save(point);

                vehicle.getPoints()
                       .add(point);
                vehiclesRepository.save(vehicle);

            }
        }
    }
}
