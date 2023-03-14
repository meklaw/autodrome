package ru.meklaw.autocontroller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.meklaw.autocontroller.dto.VehicleDTO;
import ru.meklaw.autocontroller.models.Manager;
import ru.meklaw.autocontroller.models.Vehicle;
import ru.meklaw.autocontroller.repositories.EnterprisesRepository;
import ru.meklaw.autocontroller.repositories.VehicleBrandRepository;
import ru.meklaw.autocontroller.repositories.VehiclesRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class VehiclesService {
    private final VehiclesRepository vehiclesRepository;
    private final VehicleBrandRepository vehicleBrandRepository;
    private final EnterprisesRepository enterprisesRepository;

    @Autowired
    public VehiclesService(VehiclesRepository vehiclesRepository,
                           VehicleBrandRepository vehicleBrandRepository,
                           EnterprisesRepository enterprisesRepository) {
        this.vehiclesRepository = vehiclesRepository;
        this.vehicleBrandRepository = vehicleBrandRepository;
        this.enterprisesRepository = enterprisesRepository;
    }


    public List<Vehicle> findAll(long enterpriseId, PageRequest of) {
        if (enterpriseId != -1) {
            return findAllByEnterprise(enterpriseId, of);
        }

        return findAll(of);
    }

    List<Vehicle> findAll(PageRequest of) {
        var authentication = SecurityContextHolder.getContext()
                                                  .getAuthentication();
        Manager manager = (Manager) authentication.getPrincipal();

        return vehiclesRepository.findAllByEnterprise_ManagersIn(Collections.singleton(manager),
                of.withSort(Sort.by("id")));
    }

    List<Vehicle> findAllByEnterprise(long id, PageRequest of) {
        return vehiclesRepository.findAllByEnterpriseId(id, of.withSort(Sort.by("id")));
    }

    public void create(Vehicle vehicle) {
        if (vehicle.getBuyDateTimeUtc() == null) {
            vehicle.setBuyDateTimeUtc(ZonedDateTime.now(ZoneId.of("UTC")));
        }
        vehiclesRepository.save(vehicle);
    }

    public Vehicle findById(long id) {
        return vehiclesRepository.findById(id)
                                 .orElseThrow();
    }

    public void update(long id, Vehicle vehicle) {
        vehicle.setId(id);
        vehiclesRepository.save(vehicle);
    }

    public void delete(long id) {
        vehiclesRepository.deleteById(id);
    }

    public void enriseBrand(Vehicle vehicle, VehicleDTO dto) {
        vehicle.setBrand(vehicleBrandRepository.findById(dto.getVehicleBrandId())
                                               .orElseThrow());
    }

    public void enriseEnterprise(Vehicle vehicle, VehicleDTO dto) {
        vehicle.setEnterprise(enterprisesRepository.findById(dto.getEnterpriseId())
                                                   .orElseThrow());
    }
}
