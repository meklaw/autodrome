package ru.meklaw.autodrome.controllers.rest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.dto.FullTripDTO;
import ru.meklaw.autodrome.dto.GenerateTrip;
import ru.meklaw.autodrome.dto.GpsPointDTO;
import ru.meklaw.autodrome.dto.TripDTO;
import ru.meklaw.autodrome.service.TripGpsService;
import ru.meklaw.autodrome.util.ObjectConverter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gps/trip")
public class TripGpsRestController {
    private final TripGpsService tripGpsService;
    private final ObjectConverter objectConverter;


    public TripGpsRestController(TripGpsService tripGpsService, ObjectConverter objectConverter) {
        this.tripGpsService = tripGpsService;
        this.objectConverter = objectConverter;
    }

    @GetMapping
    public FullTripDTO indexFullTrip(@RequestParam() long vehicle_id,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime time_start,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime time_end) {
        return tripGpsService.findAllTripsDTO(vehicle_id, time_start, time_end);
    }

    @GetMapping("/all")
    public List<TripDTO> indexAllTrips(@RequestParam() long vehicle_id) {
        return tripGpsService.findAllTrips(vehicle_id)
                             .stream()
                             .map(tripGpsService::convertToTripDTO)
                             .toList();
    }
    @GetMapping("/{tripId}")
    public TripDTO indexTrip(@PathVariable() long tripId) {
        return tripGpsService.convertToTripDTO(tripGpsService.findTrip(tripId));
    }


    @GetMapping("/points")
    public Object indexTripPoints(@RequestParam() long vehicle_id,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime time_start,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime time_end,
                                  @RequestParam(defaultValue = "false") boolean geoJSON) {
        List<GpsPointDTO> gpsPoints = tripGpsService.findAllPoints(vehicle_id, time_start, time_end)
                                                    .stream()
                                                    .map(tripGpsService::convertToGpsPointDTO)
                                                    .collect(Collectors.toList());
        if (geoJSON) {
            return objectConverter.convertToGeoJSON(gpsPoints);
        }

        return gpsPoints;
    }

    @GetMapping("/{tripId}/points")
    public List<GpsPointDTO> indexTripPoints(@PathVariable() long tripId) {
        return tripGpsService.findAllTripPoints(tripId)
                             .stream()
                             .map(tripGpsService::convertToGpsPointDTO)
                             .collect(Collectors.toList());
    }

    @GetMapping("/{tripId}/map")
    public String indexTripMap(@PathVariable() long tripId) {
        return tripGpsService.getMapUrlForTrip(tripId);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> generateTrip(@RequestBody GenerateTrip generateTrip) {
        if (generateTrip.getLengthKm() > 1500) {
            throw new RuntimeException("The route length is too long. The length of the route must be less than 1500 km.");
        }
        tripGpsService.generateTrip(generateTrip);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
