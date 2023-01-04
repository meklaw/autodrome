package ru.meklaw.autodrome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.meklaw.autodrome.models.Enterprise;
import ru.meklaw.autodrome.models.Manager;
import ru.meklaw.autodrome.models.Vehicle;
import ru.meklaw.autodrome.repositories.ManagerRepository;
import ru.meklaw.autodrome.repositories.VehiclesRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VehiclesService {
    private final VehiclesRepository vehiclesRepository;

    @Autowired
    public VehiclesService(VehiclesRepository vehiclesRepository) {
        this.vehiclesRepository = vehiclesRepository;
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


    public Vehicle update(Vehicle vehicle) {
        return vehiclesRepository.save(vehicle);
    }

    public void delete(long id) {
        vehiclesRepository.deleteById(id);
    }
}
