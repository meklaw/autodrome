package ru.meklaw.autodrome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.meklaw.autodrome.models.Driver;
import ru.meklaw.autodrome.models.Manager;
import ru.meklaw.autodrome.repositories.DriverRepository;

import java.util.Collections;
import java.util.List;

@Service
public class DriverService {
    final private DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }


    public List<Driver> findAllByManager() {
        var authentication = SecurityContextHolder.getContext()
                                                  .getAuthentication();
        Manager manager = (Manager) authentication.getPrincipal();

        return driverRepository.findAllByEnterprise_ManagersIn(Collections.singleton(manager));
    }
}
