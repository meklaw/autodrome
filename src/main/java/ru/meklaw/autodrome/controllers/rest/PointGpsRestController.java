package ru.meklaw.autodrome.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.dto.GpsPointDTO;
import ru.meklaw.autodrome.service.PointGpsService;
import ru.meklaw.autodrome.service.TripGpsService;
import ru.meklaw.autodrome.util.ObjectConverter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gps/point")
public class PointGpsRestController {
    private final PointGpsService pointGpsService;
    private final ObjectConverter objectConverter;
    private final TripGpsService tripGpsService;

    @Autowired
    public PointGpsRestController(PointGpsService pointGpsService,
                                  ObjectConverter objectConverter,
                                  TripGpsService tripGpsService) {
        this.pointGpsService = pointGpsService;
        this.objectConverter = objectConverter;
        this.tripGpsService = tripGpsService;
    }

    @GetMapping
    public Object index(@RequestParam(defaultValue = "-1") long vehicle_id,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<ZonedDateTime> time_start,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<ZonedDateTime> time_end,
                        @RequestParam(defaultValue = "false") boolean geoJSON) {
        List<GpsPointDTO> gpsPoints = pointGpsService.findAll(vehicle_id, time_start, time_end)
                                                     .stream()
                                                     .map(tripGpsService::convertToGpsPointDTO)
                                                     .collect(Collectors.toList());
        if (geoJSON) {
            return objectConverter.convertToGeoJSON(gpsPoints);
        }

        return gpsPoints;
    }


    @GetMapping("/{id}")
    public GpsPointDTO findById(@PathVariable long id) {
        return tripGpsService.convertToGpsPointDTO(pointGpsService.findById(id));
    }


    @GetMapping("/init")
    public void init() {
        pointGpsService.init();
    }
}
