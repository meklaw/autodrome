package ru.meklaw.autodrome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.meklaw.autodrome.models.GpsPoint;
import ru.meklaw.autodrome.repositories.TripRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final GpsPointService gpsPointService;

    @Autowired
    public TripService(TripRepository tripRepository, GpsPointService gpsPointService) {
        this.tripRepository = tripRepository;
        this.gpsPointService = gpsPointService;
    }

    public List<GpsPoint> findAllPoints(long vehicleId,
                                        ZonedDateTime timeStart,
                                        ZonedDateTime timeEnd) {

        Optional<ZonedDateTime> begin = tripRepository.findStartTripPoint(vehicleId, timeStart);
        Optional<ZonedDateTime> end = tripRepository.findEndTripPoint(vehicleId, timeEnd);

        return gpsPointService.findAll(vehicleId, begin, end);
    }
}
