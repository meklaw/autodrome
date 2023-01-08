package ru.meklaw.autodrome.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.meklaw.autodrome.dto.DriverDTO;
import ru.meklaw.autodrome.models.Driver;
import ru.meklaw.autodrome.service.DriverService;

import java.util.List;

@RestController
@RequestMapping("/api/driver")
public class DriverRestController {
    private final DriverService driverService;
    private final ModelMapper modelMapper;

    @Autowired
    public DriverRestController(DriverService driverService, ModelMapper modelMapper) {
        this.driverService = driverService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<DriverDTO> index() {
        return driverService.findAllByManager().stream().map(this::convertToDriverDTO).toList();
    }

    private DriverDTO convertToDriverDTO(Driver driver) {
        return modelMapper.map(driver, DriverDTO.class);
    }
}
