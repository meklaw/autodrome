package ru.meklaw.autodrome.controllers.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.dto.VehicleDTO;
import ru.meklaw.autodrome.models.Vehicle;
import ru.meklaw.autodrome.service.VehiclesService;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleRestController {
    private final VehiclesService vehiclesService;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleRestController(VehiclesService vehiclesService, ModelMapper modelMapper) {
        this.vehiclesService = vehiclesService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<VehicleDTO> index(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size) {
        return vehiclesService.findAllByManager(PageRequest.of(page, size))
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
