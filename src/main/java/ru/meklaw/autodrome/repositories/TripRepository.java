package ru.meklaw.autodrome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.meklaw.autodrome.models.Trip;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("select min(startTimeUtc) from Trip where  vehicle.id = :vehicleId and startTimeUtc >= :startTime")
    Optional<ZonedDateTime> findStartTripPoint(long vehicleId, ZonedDateTime startTime);

    @Query("select max(endTimeUtc) from Trip where  vehicle.id = :vehicleId and endTimeUtc <= :endTime")
    Optional<ZonedDateTime> findEndTripPoint(long vehicleId, ZonedDateTime endTime);

    List<Trip> findAllByVehicleIdAndStartTimeUtcIsGreaterThanEqualAndEndTimeUtcLessThanEqual(Long vehicle_id,
                                                                                             ZonedDateTime startTimeUtc,
                                                                                             ZonedDateTime endTimeUtc);

    List<Trip> findAllByVehicleId(long vehicleId);
}
