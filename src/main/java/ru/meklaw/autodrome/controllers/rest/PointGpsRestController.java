package ru.meklaw.autodrome.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.dto.GpsPointDTO;
import ru.meklaw.autodrome.service.GpsPointService;
import ru.meklaw.autodrome.util.ObjectConverter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gps/point")
public class PointGpsRestController {
    private final GpsPointService gpsPointService;
    private final ObjectConverter objectConverter;

    @Autowired
    public PointGpsRestController(GpsPointService gpsPointService, ObjectConverter objectConverter) {
        this.gpsPointService = gpsPointService;
        this.objectConverter = objectConverter;
    }

    @GetMapping
    public Object index(@RequestParam(defaultValue = "-1") long vehicle_id,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<ZonedDateTime> time_start,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<ZonedDateTime> time_end,
                        @RequestParam(defaultValue = "false") boolean geoJSON) {
        List<GpsPointDTO> gpsPoints = gpsPointService.findAll(vehicle_id, time_start, time_end)
                                                     .stream()
                                                     .map(objectConverter::convertToGpsPointDTO)
                                                     .collect(Collectors.toList());
        if (geoJSON) {
            return objectConverter.convertToGeoJSON(gpsPoints);
        }

        return gpsPoints;
    }


    @GetMapping("/{id}")
    public GpsPointDTO findById(@PathVariable long id) {
        return objectConverter.convertToGpsPointDTO(gpsPointService.findById(id));
    }


    @GetMapping("/init")
    public void init() {
        gpsPointService.init();
    }
}
