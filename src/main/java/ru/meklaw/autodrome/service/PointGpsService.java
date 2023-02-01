package ru.meklaw.autodrome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meklaw.autodrome.models.GpsPoint;
import ru.meklaw.autodrome.repositories.GpsPointRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PointGpsService {
    private final GpsPointRepository gpsPointRepository;

    @Autowired
    public PointGpsService(GpsPointRepository gpsPointRepository) {
        this.gpsPointRepository = gpsPointRepository;
    }

    @Transactional(readOnly = true)
    public List<GpsPoint> findAll(long vehicleId, Optional<ZonedDateTime> startTime, Optional<ZonedDateTime> endTime) {
        List<GpsPoint> points = find(vehicleId, startTime, endTime);
        for (GpsPoint point : points) {
            point.setDateTime(point.getDateTime()
                                   .withZoneSameInstant(point.getVehicle()
                                                             .getEnterprise()
                                                             .getTimeZone()));
        }

        return points;
    }

    @Transactional(readOnly = true)
    List<GpsPoint> find(long vehicleId, Optional<ZonedDateTime> startTime, Optional<ZonedDateTime> endTime) {
        if (vehicleId != -1 && startTime.isPresent() && endTime.isPresent()) {
            return gpsPointRepository.findAllByVehicleIdAndDateTimeBetweenOrderByDateTimeAsc(vehicleId,
                    startTime.get(),
                    endTime.get());
        }

        if (vehicleId != -1 && startTime.isPresent()) {
            return gpsPointRepository.findAllByVehicleIdAndDateTimeAfterOrderByDateTimeAsc(vehicleId, startTime.get());
        }

        if (vehicleId != -1 && endTime.isPresent()) {
            return gpsPointRepository.findAllByVehicleIdAndDateTimeBeforeOrderByDateTimeAsc(vehicleId, endTime.get());
        }

        if (vehicleId != -1) {
            return gpsPointRepository.findAllByVehicleIdOrderByDateTimeAsc(vehicleId);
        }

        if (startTime.isPresent() && endTime.isPresent()) {
            return gpsPointRepository.findAllByDateTimeBetweenOrderByDateTimeAsc(startTime.get(), endTime.get());
        }

        if (startTime.isPresent()) {
            return gpsPointRepository.findAllByDateTimeAfterOrderByDateTimeAsc(startTime.get());
        }

        if (endTime.isPresent()) {
            return gpsPointRepository.findAllByDateTimeBeforeOrderByDateTimeAsc(endTime.get());
        }

        return gpsPointRepository.findAll();
    }

    @Transactional(readOnly = true)
    public GpsPoint findById(long id) {
        return gpsPointRepository.findById(id)
                                 .orElseThrow();
    }
}
