package ru.meklaw.autodrome.controllers.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.dto.GpsPointDTO;
import ru.meklaw.autodrome.models.GpsPoint;
import ru.meklaw.autodrome.service.GpsPointService;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gps")
public class GpsPointRestController {

    private final ModelMapper modelMapper;
    private final GpsPointService gpsPointService;

    @Autowired
    public GpsPointRestController(ModelMapper modelMapper, GpsPointService gpsPointService) {
        this.modelMapper = modelMapper;
        this.gpsPointService = gpsPointService;
    }

    @GetMapping
    public Object index(
            @RequestParam(defaultValue = "-1") long vehicle_id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<ZonedDateTime> time_start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<ZonedDateTime> time_end,
            @RequestParam(defaultValue = "false") boolean geoJSON) {
        List<GpsPointDTO> gpsPoints = gpsPointService.findAll(vehicle_id, time_start, time_end)
                                                     .stream()
                                                     .map(this::convertToGpsPointDTO)
                                                     .collect(Collectors.toList());
        if (geoJSON)
            return convertToGeoJSON(gpsPoints);

        return gpsPoints;
    }


    @GetMapping("/{id}")
    public GpsPointDTO findById(@PathVariable long id) {
        return convertToGpsPointDTO(gpsPointService.findById(id));
    }

    @GetMapping("/init")
    public void init() {
        gpsPointService.init();
    }

    private GpsPointDTO convertToGpsPointDTO(GpsPoint point) {
        return modelMapper.map(point, GpsPointDTO.class);
    }

    private Object convertToGeoJSON(List<GpsPointDTO> gpsPoints) {
        List<Map<String, Object>> features = new ArrayList<>();

        for (GpsPointDTO point : gpsPoints) {
            Map<String, Object> feature = new HashMap<>();
            Map<String, Object> geometry = new HashMap<>();
            Map<String, Object> properties = new HashMap<>();

            geometry.put("type", "Point");
            geometry.put("coordinates", Arrays.asList(point.getX(), point.getY()));

            properties.put("id", point.getId());
            properties.put("dateTime", point.getDateTime());

            feature.put("type", "Feature");
            feature.put("geometry", geometry);
            feature.put("properties", properties);

            features.add(feature);
        }

        Map<String, Object> geoJSON = new HashMap<>();
        geoJSON.put("type", "FeatureCollection");
        geoJSON.put("features", features);

        return geoJSON;
    }

}

