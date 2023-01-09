package ru.meklaw.autodrome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.meklaw.autodrome.dto.VehicleDTO;
import ru.meklaw.autodrome.models.Person;
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

    public Page<Vehicle> findAll(PageRequest of) {
        return vehiclesRepository.findAll(of.withSort(Sort.by("id")));
    }

    public List<Vehicle> findAllByManager(PageRequest of) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = (Person) authentication.getPrincipal();

        return vehiclesRepository.findAllByEnterprise_ManagersIn(
                Collections.singleton(person.getManager()),
                of.withSort(Sort.by("id"))
        );
    }

    public void create(Vehicle vehicle) {
        vehiclesRepository.save(vehicle);
    }

    public Optional<Vehicle> findById(long id) {
        return vehiclesRepository.findById(id);
    }

    public void update(long id, Vehicle vehicle) {
        vehicle.setId(id);
        vehiclesRepository.save(vehicle);
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
