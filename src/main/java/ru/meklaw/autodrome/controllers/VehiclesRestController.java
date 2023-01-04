package ru.meklaw.autodrome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.meklaw.autodrome.dto.VehicleDTO;
import ru.meklaw.autodrome.models.Vehicle;
import ru.meklaw.autodrome.security.ManagerDetails;
import ru.meklaw.autodrome.service.VehiclesService;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehiclesRestController {
    private final VehiclesService vehiclesService;

    @Autowired
    public VehiclesRestController(VehiclesService vehiclesService) {
        this.vehiclesService = vehiclesService;
    }

    @GetMapping
    public List<VehicleDTO> index() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        ManagerDetails managerDetails = (ManagerDetails) authentication.getPrincipal();

        return vehiclesService.findAllByManager(managerDetails.getManager())
                .stream()
                .map(this::convertToVehicleDTO)
                .toList();
    }

    private VehicleDTO convertToVehicleDTO(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();

        dto.setId(vehicle.getId());
        dto.setNumber(vehicle.getNumber());
        dto.setCost(vehicle.getCost());
        dto.setYearOfProduction(vehicle.getYearOfProduction());
        dto.setMileage(vehicle.getMileage());
        dto.setBrand(vehicle.getBrand().getId());

        return dto;
    }

}
