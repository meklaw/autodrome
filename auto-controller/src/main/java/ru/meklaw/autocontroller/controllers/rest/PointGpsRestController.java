package ru.meklaw.autocontroller.controllers.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import ru.meklaw.autocontroller.dto.GpsPointDTO;
import ru.meklaw.autocontroller.models.GpsPoint;
import ru.meklaw.autocontroller.repositories.PointGpsRepository;
import ru.meklaw.autocontroller.service.PointGpsService;
import ru.meklaw.autocontroller.util.ObjectConverter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gps/point")
public class PointGpsRestController {
    private final PointGpsService pointGpsService;
    private final PointGpsRepository pointGpsRepository;
    private final ObjectConverter objectConverter;
    private final ModelMapper modelMapper;

    @Autowired
    public PointGpsRestController(PointGpsService pointGpsService,
                                  PointGpsRepository pointGpsRepository, ObjectConverter objectConverter, ModelMapper modelMapper) {
        this.pointGpsService = pointGpsService;
        this.pointGpsRepository = pointGpsRepository;
        this.objectConverter = objectConverter;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public Object index(@RequestParam(defaultValue = "-1") long vehicle_id,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<ZonedDateTime> time_start,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<ZonedDateTime> time_end,
                        @RequestParam(defaultValue = "false") boolean geoJSON) {
        List<GpsPointDTO> gpsPoints = pointGpsService.findAll(vehicle_id, time_start, time_end)
                                                     .stream()
                                                     .map(this::convertToGpsPointDTO)
                                                     .collect(Collectors.toList());
        if (geoJSON) {
            return objectConverter.convertToGeoJSON(gpsPoints);
        }

        return gpsPoints;
    }


    @GetMapping("/{id}")
    public GpsPointDTO findById(@PathVariable long id) {
        return this.convertToGpsPointDTO(pointGpsRepository.findById(id).orElseThrow());
    }

    public GpsPointDTO convertToGpsPointDTO(GpsPoint point) {
        return modelMapper.map(point, GpsPointDTO.class);
    }
}
