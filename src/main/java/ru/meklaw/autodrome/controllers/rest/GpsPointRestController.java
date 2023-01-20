package ru.meklaw.autodrome.controllers.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.dto.GpsPointDTO;
import ru.meklaw.autodrome.models.GpsPoint;
import ru.meklaw.autodrome.service.GpsPointService;

import java.util.List;

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
    public List<GpsPointDTO> index(@RequestParam(defaultValue = "-1") long vehicle_id) {
        return gpsPointService.findAll(vehicle_id)
                              .stream()
                              .map(this::convertToGpsPointDTO)
                              .toList();
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
}

