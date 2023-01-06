package ru.meklaw.autodrome.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.dto.VehicleDTO;
import ru.meklaw.autodrome.models.Vehicle;
import ru.meklaw.autodrome.security.ManagerDetails;
import ru.meklaw.autodrome.service.VehiclesService;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehiclesRestController {
    private final VehiclesService vehiclesService;
    private final ModelMapper modelMapper;

    @Autowired
    public VehiclesRestController(VehiclesService vehiclesService, ModelMapper modelMapper) {
        this.vehiclesService = vehiclesService;
        this.modelMapper = modelMapper;
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

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody VehicleDTO vehicleDTO) {
        vehiclesService.create(convertToVehicle(vehicleDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable long id, @RequestBody VehicleDTO vehicleDTO) {
        vehiclesService.update(id, convertToVehicle(vehicleDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        vehiclesService.delete(id);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    private VehicleDTO convertToVehicleDTO(Vehicle vehicle) {
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    private Vehicle convertToVehicle(VehicleDTO dto) {
        Vehicle vehicle = modelMapper.map(dto, Vehicle.class);

        vehiclesService.enriseBrand(vehicle, dto);
        vehiclesService.enriseEnterprise(vehicle, dto);

        return vehicle;
    }

}
