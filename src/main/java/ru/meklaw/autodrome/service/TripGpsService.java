package ru.meklaw.autodrome.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.meklaw.autodrome.dto.*;
import ru.meklaw.autodrome.models.GpsPoint;
import ru.meklaw.autodrome.models.Trip;
import ru.meklaw.autodrome.repositories.TripRepository;
import ru.meklaw.autodrome.repositories.VehiclesRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TripGpsService {
    private final TripRepository tripRepository;
    private final PointGpsService pointGpsService;
    private final VehiclesRepository vehiclesRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    @Value("${graphhopper.api.key}")
    private String graphhopperApiKey;

    @Autowired
    public TripGpsService(TripRepository tripRepository,
                          PointGpsService pointGpsService,
                          VehiclesRepository vehiclesRepository,
                          ModelMapper modelMapper,
                          RestTemplate restTemplate) {
        this.tripRepository = tripRepository;
        this.pointGpsService = pointGpsService;
        this.vehiclesRepository = vehiclesRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
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
    public Trip findTrip(long tripId) {
        return tripRepository.findById(tripId)
                             .orElseThrow();
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
                                      .map(this::convertToTripDTO)
                                      .toList())
                          .startDateTime(trips.get(0)
                                              .getStartTimeUtc())
                          .endDateTime(trips.get(trips.size() - 1)
                                            .getEndTimeUtc())
                          .startPoint(convertToGpsPointDTO(points.get(0)))
                          .endPoint(convertToGpsPointDTO(points.get(points.size() - 1)))
                          .build();
    }

    @NotNull
    public String findAddress(GpsPoint point) {
        String url = "https://graphhopper.com/api/1/geocode?reverse=true&key=" + graphhopperApiKey + "&point=" + point.getLat() + "," + point.getLon();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(response.getBody())
                                  .get("hits")
                                  .get(0);

            return root.get("country").asText()
                    + root.get("state").asText("")
                    + root.get("city").asText("")
                    + root.get("street").asText("");
        } catch (NullPointerException | JsonProcessingException ignored) {
        }
        return "address not found";
    }

    public GpsPointDTO convertToGpsPointDTO(GpsPoint point) {
        GpsPointDTO gpsPointDTO = modelMapper.map(point, GpsPointDTO.class);
        gpsPointDTO.setAddress(findAddress(point));

        return gpsPointDTO;
    }

    public TripDTO convertToTripDTO(Trip trip) {
        return modelMapper.map(trip, TripDTO.class);
    }

    @Transactional(readOnly = true)
    public List<GpsPoint> findAllTripPoints(long tripId) {
        Trip trip = tripRepository.findById(tripId)
                                  .orElseThrow();
        return findAllPoints(trip.getVehicle()
                                 .getId(), trip.getStartTimeUtc(), trip.getEndTimeUtc());
    }

    public String getMapUrlForTrip(long tripId) {
        return getMapUrlForTrip(findAllTripPoints(tripId));
    }

    public String getMapUrlForTrip(List<GpsPoint> tripPoints) {
        val mapUrl = new StringBuilder("https://graphhopper.com/maps/?profile=car&layer=Omniscale");

        for (int i = 0, point = 0, delta = tripPoints.size() / 80 + 1;
             i < 79 && point < tripPoints.size();
             i++, point += delta) {

            mapUrl.append("&point=");
            mapUrl.append(tripPoints.get(point)
                                    .getLat());
            mapUrl.append("%2C");
            mapUrl.append(tripPoints.get(point)
                                    .getLon());
        }
        mapUrl.append("&point=");
        mapUrl.append(tripPoints.get(tripPoints.size() - 1)
                                .getLat());
        mapUrl.append("%2C");
        mapUrl.append(tripPoints.get(tripPoints.size() - 1)
                                .getLon());
        return mapUrl.toString();
    }

    public void generateTrip(GenerateTrip generateTrip) {
        Trip resultTrip = new Trip();
        resultTrip.setVehicle(vehiclesRepository.findById(generateTrip.getVehicleId())
                                                .orElseThrow(() -> new RuntimeException(
                                                        "Vehicle with this id doesn't exist")));
        List<GpsPoint> randomGpsPoints = generateRandomPoints();
        RoutingTripPoints dirtyNewTrip = routingPointsToTrip(randomGpsPoints.get(0), randomGpsPoints.get(1));
        int dirtyCountPoints = dirtyNewTrip.getPoints()
                                           .size();
        double pointsPerKm = ((double) dirtyCountPoints) / (dirtyNewTrip.getDistanceMeters() / 1000);

        int requiredNumberPoints = (int) (pointsPerKm * generateTrip.getLengthKm());
        int randomStartIndex = (int) (Math.random() * (dirtyCountPoints - requiredNumberPoints));

        if (randomStartIndex < 0 || dirtyCountPoints <= randomStartIndex + requiredNumberPoints) {
            generateTrip(generateTrip);
            return;
        }
        List<GpsPoint> resultPointsOfTrip = dirtyNewTrip.getPoints()
                                                        .subList(randomStartIndex,
                                                                randomStartIndex + requiredNumberPoints);

        double speedMpS = generateTrip.getMaxSpeedKph() / 2 / 3.6;
        double metersPerPoint = dirtyNewTrip.getDistanceMeters() / ((double) dirtyCountPoints);
        long deltaTimeAndPoint = (long) (metersPerPoint / speedMpS);

        resultTrip.setStartTimeUtc(ZonedDateTime.now(ZoneId.of("UTC")));
        ZonedDateTime dateTime = resultTrip.getStartTimeUtc();

        for (GpsPoint point : resultPointsOfTrip) {
            point.setVehicle(resultTrip.getVehicle());
            point.setDateTime(dateTime);
            dateTime = dateTime.withZoneSameInstant(ZoneId.of("UTC"))
                               .plusSeconds(deltaTimeAndPoint);
        }
        resultTrip.setEndTimeUtc(dateTime);
        resultTrip.setLengthKm(generateTrip.getLengthKm());

        tripRepository.save(resultTrip);
        pointGpsService.saveAll(resultPointsOfTrip);
    }

    public RoutingTripPoints routingPointsToTrip(GpsPoint startGpsPoint,
                                                 GpsPoint endGpsPoint) {
        val url = "https://graphhopper.com/api/1/route?profile=car&locale=en&points_encoded=false&point="
                + startGpsPoint.getLat() + "," + startGpsPoint.getLon()
                + "&point=" + endGpsPoint.getLat() + "," + endGpsPoint.getLon()
                + "&key=" + graphhopperApiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        val mapper = new ObjectMapper();

        val gpsPoints = new ArrayList<GpsPoint>();
        try {
            JsonNode pathsNode = mapper.readTree(response.getBody())
                                       .path("paths")
                                       .get(0);
            double distance = pathsNode.get("distance")
                                       .asDouble();
            pathsNode.path("points")
                     .get("coordinates")
                     .forEach(coordinateNode ->
                             gpsPoints.add(GpsPoint.builder()
                                                   .lon(coordinateNode.get(0)
                                                                      .asDouble())
                                                   .lat(coordinateNode.get(1)
                                                                      .asDouble())
                                                   .build()));

            return new RoutingTripPoints(gpsPoints, distance);

        } catch (NullPointerException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<GpsPoint> generateRandomPoints() {
        List<Double> points = List.of(55.784945, 37.565977,
                55.789110, 37.681950,
                55.824380, 37.572920,
                55.747420, 37.697870,
                55.749045, 37.611690,
                55.708640, 37.581360,
                55.693870, 37.734510,
                55.707650, 37.835063,
                55.801688, 37.322621,
                55.796478, 37.964279,
                55.934713, 37.546760,
                55.438432, 37.556057,
                59.119960, 37.902756,
                56.008830, 37.853389,
                59.893800, 30.242890,
                59.216127, 39.886872,
                48.770732, 2.586623,
                59.328785, 8.088549,
                39.613125, 16.231488,
                43.156063, 131.912766,
                41.000344, 28.810442,
                19.872946, 75.327196,
                47.019841, 28.839209,
                47.461521, 19.049556,
                46.952052, 7.458751);

        List<GpsPoint> randomGpsPoints = IntStream.iterate(0, n -> n < points.size(), n -> n + 2)
                                                  .mapToObj(n ->
                                                          GpsPoint.builder()
                                                                  .lat(points.get(n))
                                                                  .lon(points.get(n + 1))
                                                                  .build()
                                                  )
                                                  .collect(Collectors.toList());
        Collections.shuffle(randomGpsPoints);
        return randomGpsPoints;
    }
}
