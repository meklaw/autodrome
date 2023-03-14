package ru.meklaw.autocontroller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.meklaw.autocontroller.models.GpsPoint;
import ru.meklaw.autocontroller.repositories.PointGpsRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PointGpsService {
    private final PointGpsRepository pointGpsRepository;

    @Autowired
    public PointGpsService(PointGpsRepository pointGpsRepository) {
        this.pointGpsRepository = pointGpsRepository;
    }

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

    List<GpsPoint> find(long vehicleId, Optional<ZonedDateTime> startTime, Optional<ZonedDateTime> endTime) {
        if (vehicleId != -1 && startTime.isPresent() && endTime.isPresent()) {
            return pointGpsRepository.findAllByVehicleIdAndDateTimeBetweenOrderByDateTimeAsc(vehicleId,
                    startTime.get(),
                    endTime.get());
        }

        if (vehicleId != -1 && startTime.isPresent()) {
            return pointGpsRepository.findAllByVehicleIdAndDateTimeAfterOrderByDateTimeAsc(vehicleId, startTime.get());
        }

        if (vehicleId != -1 && endTime.isPresent()) {
            return pointGpsRepository.findAllByVehicleIdAndDateTimeBeforeOrderByDateTimeAsc(vehicleId, endTime.get());
        }

        if (vehicleId != -1) {
            return pointGpsRepository.findAllByVehicleIdOrderByDateTimeAsc(vehicleId);
        }

        if (startTime.isPresent() && endTime.isPresent()) {
            return pointGpsRepository.findAllByDateTimeBetweenOrderByDateTimeAsc(startTime.get(), endTime.get());
        }

        if (startTime.isPresent()) {
            return pointGpsRepository.findAllByDateTimeAfterOrderByDateTimeAsc(startTime.get());
        }

        if (endTime.isPresent()) {
            return pointGpsRepository.findAllByDateTimeBeforeOrderByDateTimeAsc(endTime.get());
        }

        return pointGpsRepository.findAll();
    }
}
