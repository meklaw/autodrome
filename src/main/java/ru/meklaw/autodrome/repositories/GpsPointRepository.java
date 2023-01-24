package ru.meklaw.autodrome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meklaw.autodrome.models.GpsPoint;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface GpsPointRepository extends JpaRepository<GpsPoint, Long> {
    List<GpsPoint> findAllByVehicleIdOrderByDateTimeAsc(long vehicleId);

    List<GpsPoint> findAllByVehicleIdAndDateTimeBetweenOrderByDateTimeAsc(long vehicleId, ZonedDateTime timeStart, ZonedDateTime timeEnd);

    List<GpsPoint> findAllByVehicleIdAndDateTimeAfterOrderByDateTimeAsc(long vehicleId, ZonedDateTime timeStart);

    List<GpsPoint> findAllByVehicleIdAndDateTimeBeforeOrderByDateTimeAsc(long vehicleId, ZonedDateTime timeEnd);

    List<GpsPoint> findAllByDateTimeBetweenOrderByDateTimeAsc(ZonedDateTime timeStart, ZonedDateTime timeEnd);

    List<GpsPoint> findAllByDateTimeAfterOrderByDateTimeAsc(ZonedDateTime timeStart);

    List<GpsPoint> findAllByDateTimeBeforeOrderByDateTimeAsc(ZonedDateTime timeEnd);

}
