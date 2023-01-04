package ru.meklaw.autodrome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.meklaw.autodrome.dto.DriverDTO;
import ru.meklaw.autodrome.models.Driver;
import ru.meklaw.autodrome.models.Manager;
import ru.meklaw.autodrome.service.DriverService;
import ru.meklaw.autodrome.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping("/api/driver")
public class DriverRestController {
    private final DriverService driverService;

    @Autowired
    public DriverRestController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public List<DriverDTO> index() {
        return driverService.findAll().stream().map(this::convertToDriverDTO).toList();
    }

    private DriverDTO convertToDriverDTO(Driver driver) {
        DriverDTO dto = new DriverDTO();

        dto.setId(driver.getId());
        dto.setName(driver.getName());
        dto.setSalary(driver.getSalary());
        dto.setActive(driver.isActive());
        dto.setEnterprise(driver.getEnterprise().getId());
        dto.setVehicle(driver.getVehicle().getId());

        return dto;
    }
}
