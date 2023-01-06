package ru.meklaw.autodrome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.meklaw.autodrome.dto.VehicleDTO;
import ru.meklaw.autodrome.models.Manager;
import ru.meklaw.autodrome.models.Vehicle;
import ru.meklaw.autodrome.repositories.EnterprisesRepository;
import ru.meklaw.autodrome.repositories.VehicleBrandRepository;
import ru.meklaw.autodrome.repositories.VehiclesRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VehiclesService {
    private final VehiclesRepository vehiclesRepository;
    private final VehicleBrandRepository vehicleBrandRepository;
    private final EnterprisesRepository enterprisesRepository;

    @Autowired
    public VehiclesService(VehiclesRepository vehiclesRepository, VehicleBrandRepository vehicleBrandRepository, EnterprisesRepository enterprisesRepository) {
        this.vehiclesRepository = vehiclesRepository;
        this.vehicleBrandRepository = vehicleBrandRepository;
        this.enterprisesRepository = enterprisesRepository;
    }

    public List<Vehicle> findAll() {
        return vehiclesRepository.findAll();
    }

    public List<Vehicle> findAllByManager(Manager manager) {
        return vehiclesRepository.findAllByEnterprise_ManagersIn(Collections.singleton(manager));
    }

    public Vehicle create(Vehicle vehicle) {
        return vehiclesRepository.save(vehicle);
    }

    public Optional<Vehicle> findById(long id) {
        return vehiclesRepository.findById(id);
    }

    public Vehicle update(long id, Vehicle vehicle) {
        vehicle.setId(id);
        return vehiclesRepository.save(vehicle);
    }

    public void delete(long id) {
        vehiclesRepository.deleteById(id);
    }

    public void enriseBrand(Vehicle vehicle, VehicleDTO dto) {
        vehicle.setBrand(vehicleBrandRepository.findById(dto.getVehicleBrandId()).orElseThrow());
    }

    public void enriseEnterprise(Vehicle vehicle, VehicleDTO dto) {
        vehicle.setEnterprise(enterprisesRepository.findById(dto.getEnterpriseId()).orElseThrow());
    }
}
