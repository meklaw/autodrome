package ru.meklaw.autodrome.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.meklaw.autodrome.dto.DriverDTO;
import ru.meklaw.autodrome.service.DriverService;
import ru.meklaw.autodrome.util.ObjectConverter;

import java.util.List;

@RestController
@RequestMapping("/api/driver")
public class DriverRestController {
    private final DriverService driverService;
    private final ObjectConverter objectConverter;

    @Autowired
    public DriverRestController(DriverService driverService, ObjectConverter objectConverter) {
        this.driverService = driverService;
        this.objectConverter = objectConverter;
    }

    @GetMapping
    public List<DriverDTO> index() {
        return driverService.findAllByManager()
                            .stream()
                            .map(objectConverter::convertToDriverDTO)
                            .toList();
    }

}
