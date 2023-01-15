package ru.meklaw.autodrome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meklaw.autodrome.dto.FillEnterprisesDTO;
import ru.meklaw.autodrome.models.*;
import ru.meklaw.autodrome.repositories.DriverRepository;
import ru.meklaw.autodrome.repositories.EnterprisesRepository;
import ru.meklaw.autodrome.repositories.VehicleBrandRepository;
import ru.meklaw.autodrome.repositories.VehiclesRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class EnterprisesService {
    private final EnterprisesRepository enterprisesRepository;
    private final VehicleBrandRepository vehicleBrandRepository;
    private final VehiclesRepository vehiclesRepository;
    private final DriverRepository driverRepository;

    @Autowired
    public EnterprisesService(EnterprisesRepository enterprisesRepository, VehicleBrandRepository vehicleBrandRepository, VehiclesRepository vehiclesRepository, DriverRepository driverRepository) {
        this.enterprisesRepository = enterprisesRepository;
        this.vehicleBrandRepository = vehicleBrandRepository;
        this.vehiclesRepository = vehiclesRepository;
        this.driverRepository = driverRepository;
    }

    public List<Enterprise> findAllByManager() {
        var authentication = SecurityContextHolder.getContext()
                                                  .getAuthentication();
        Manager manager = (Manager) authentication.getPrincipal();

        return enterprisesRepository.findAllByManagersIn(Collections.singleton(manager));
    }

    @Transactional
    public List<Enterprise> findAll() {
        return enterprisesRepository.findAll();
    }

    @Transactional
    public List<Enterprise> init(List<FillEnterprisesDTO> enterprises) {
        enterprises.forEach(this::fillEnterprise);
        return enterprisesRepository.findAllByIdIn(
                enterprises.stream()
                           .map(FillEnterprisesDTO::getEnterpriseId)
                           .toList()
        );
    }

    @Transactional
    void fillEnterprise(FillEnterprisesDTO dto) {
        Enterprise enterprise = enterprisesRepository.findById(dto.getEnterpriseId())
                                                     .orElseThrow();
        List<VehicleBrand> brands = vehicleBrandRepository.findAll();
        Random random = new Random();

        for (int i = 0; i < dto.getCountVehicles(); i++) {
            Vehicle vehicle = new Vehicle();
            vehicle.setNumber("Car " + enterprise.getId() + i);
            vehicle.setCost(random.nextInt(10000));
            vehicle.setYearOfProduction(random.nextInt(2023 - 1900 + 1) + 1900);
            vehicle.setMileage(random.nextInt(100000));

            vehicle.setBrand(brands.get(i % brands.size()));
            brands.get(i % brands.size())
                  .getVehicles()
                  .add(vehicle);

            vehicle.setEnterprise(enterprise);
            enterprise.getVehicles()
                      .add(vehicle);

            Driver driver = new Driver();
            driver.setName("Bob " + enterprise.getId() + i);
            driver.setSalary(random.nextInt(1000));
            driver.setActive(random.nextInt(10) == 0);

            driver.setEnterprise(enterprise);
            enterprise.getDrivers()
                      .add(driver);

            driver.setVehicle(vehicle);
            if (vehicle.getDrivers() == null)
                vehicle.setDrivers(new ArrayList<>());
            vehicle.getDrivers()
                   .add(driver);

            driverRepository.save(driver);
            vehiclesRepository.save(vehicle);
        }

    }


}
