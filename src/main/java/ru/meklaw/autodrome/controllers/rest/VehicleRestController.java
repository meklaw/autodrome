package ru.meklaw.autodrome.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.dto.VehicleDTO;
import ru.meklaw.autodrome.models.Enterprise;
import ru.meklaw.autodrome.service.EnterprisesService;
import ru.meklaw.autodrome.service.VehiclesService;
import ru.meklaw.autodrome.util.ObjectConverter;

import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleRestController {
    private final VehiclesService vehiclesService;
    private final EnterprisesService enterprisesService;
    private final ObjectConverter objectConverter;

    @Autowired
    public VehicleRestController(VehiclesService vehiclesService,
                                 EnterprisesService enterprisesService,
                                 ObjectConverter objectConverter) {
        this.vehiclesService = vehiclesService;
        this.enterprisesService = enterprisesService;
        this.objectConverter = objectConverter;
    }

    @GetMapping
    public List<VehicleDTO> index(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size,
                                  @RequestParam(defaultValue = "-1") long enterprise_id) {

        List<VehicleDTO> vehicles = vehiclesService.findAll(enterprise_id, PageRequest.of(page, size))
                                                   .stream()
                                                   .map(objectConverter::convertToVehicleDTO)
                                                   .toList();


        if (enterprise_id != -1) {
            Enterprise enterprise = enterprisesService.findById(enterprise_id);

            vehicles.forEach(vehicle -> setWithZoneSameInstant(vehicle, enterprise.getTimeZone()));

            return vehicles;
        }
//        TODO take timezone of manager and move this logic to service
        vehicles.forEach(vehicle -> setWithZoneSameInstant(vehicle, ZoneId.of("Europe/Moscow")));

        return vehicles;
    }

    private void setWithZoneSameInstant(VehicleDTO vehicle, ZoneId zoneId) {
        vehicle.setBuyDateTimeUtc(vehicle.getBuyDateTimeUtc()
                                         .withZoneSameInstant(zoneId));
    }

    @GetMapping("/{id}")
    public VehicleDTO findById(@PathVariable long id) {
        VehicleDTO dto = objectConverter.convertToVehicleDTO(vehiclesService.findById(id));
        setWithZoneSameInstant(dto, ZoneId.systemDefault());

        return dto;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody VehicleDTO vehicleDTO) {
        vehiclesService.create(objectConverter.convertToVehicle(vehicleDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable long id, @RequestBody VehicleDTO vehicleDTO) {
        vehiclesService.update(id, objectConverter.convertToVehicle(vehicleDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        vehiclesService.delete(id);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

}
