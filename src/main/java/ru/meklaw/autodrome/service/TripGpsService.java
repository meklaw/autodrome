package ru.meklaw.autodrome.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.meklaw.autodrome.dto.FullTripDTO;
import ru.meklaw.autodrome.models.GpsPoint;
import ru.meklaw.autodrome.models.Trip;
import ru.meklaw.autodrome.repositories.TripRepository;
import ru.meklaw.autodrome.repositories.VehiclesRepository;
import ru.meklaw.autodrome.util.ObjectConverter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TripGpsService {
    private final TripRepository tripRepository;
    private final PointGpsService pointGpsService;
    private final VehiclesRepository vehiclesRepository;
    private final ObjectConverter objectConverter;

    @Value("${yandex.geocoder.api.key}")
    private String yandexApiKey;

    @Autowired
    public TripGpsService(TripRepository tripRepository,
                          PointGpsService pointGpsService,
                          VehiclesRepository vehiclesRepository, ObjectConverter objectConverter) {
        this.tripRepository = tripRepository;
        this.pointGpsService = pointGpsService;
        this.vehiclesRepository = vehiclesRepository;
        this.objectConverter = objectConverter;
    }

    @Transactional(readOnly = true)
    public List<GpsPoint> findAllPoints(long vehicleId, ZonedDateTime timeStart, ZonedDateTime timeEnd) {

        Optional<ZonedDateTime> begin = tripRepository.findStartTripPoint(vehicleId, timeStart);
        Optional<ZonedDateTime> end = tripRepository.findEndTripPoint(vehicleId, timeEnd);

        return pointGpsService.findAll(vehicleId, begin, end);
    }

    @Transactional(readOnly = true)
    public List<Trip> findAllTrips(long vehicleId) {
        return tripRepository.findAllByVehicleIdOrderByStartTimeUtc(vehicleId);
    }

    @Transactional(readOnly = true)
    public FullTripDTO findAllTripsDTO(long vehicleId, ZonedDateTime startTime, ZonedDateTime endTime) {
        ZoneId enterpriseTimeZone = vehiclesRepository.findById(vehicleId)
                                                      .orElseThrow()
                                                      .getEnterprise()
                                                      .getTimeZone();
        List<Trip> trips = tripRepository.findAllByVehicleIdAndStartTimeUtcIsGreaterThanEqualAndEndTimeUtcLessThanEqualOrderByStartTimeUtc(
                                                 vehicleId,
                                                 startTime,
                                                 endTime)
                                         .stream()
                                         .peek(trip -> {
                                             trip.setStartTimeUtc(trip.getStartTimeUtc()
                                                                      .withZoneSameInstant(enterpriseTimeZone));
                                             trip.setEndTimeUtc(trip.getEndTimeUtc()
                                                                    .withZoneSameInstant(enterpriseTimeZone));
                                         })
                                         .toList();
        List<GpsPoint> points = pointGpsService.find(vehicleId,
                                                       Optional.ofNullable(startTime),
                                                       Optional.ofNullable(endTime))
                                               .stream()
                                               .peek(point -> point.setDateTime(point.getDateTime()
                                                                                     .withZoneSameInstant(
                                                                                             enterpriseTimeZone)))
                                               .toList();


        return FullTripDTO.builder()
                          .trips(trips.stream()
                                      .map(objectConverter::convertToTripDTO)
                                      .toList())
                          .startDateTime(trips.get(0)
                                              .getStartTimeUtc())
                          .endDateTime(trips.get(trips.size() - 1)
                                            .getEndTimeUtc())
                          .startPoint(objectConverter.convertToGpsPointDTO(points.get(0)))
                          .endPoint(objectConverter.convertToGpsPointDTO(points.get(points.size() - 1)))
                          .build();
    }

    @NotNull
    public String findAddress(GpsPoint point) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://geocode-maps.yandex.ru/1.x/?apikey=" + yandexApiKey + "&format=json&geocode=" + point.getX() + "," + point.getY();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(response.getBody());
            return root.get("response")
                       .get("GeoObjectCollection")
                       .get("featureMember")
                       .get(0)
                       .get("GeoObject")
                       .get("metaDataProperty")
                       .get("GeocoderMetaData")
                       .get("text")
                       .asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
