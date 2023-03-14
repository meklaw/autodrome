package ru.meklaw.autocontroller.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.meklaw.autocontroller.dto.DriverDTO;
import ru.meklaw.autocontroller.repositories.DriverRepository;
import ru.meklaw.autocontroller.service.ManagerService;
import ru.meklaw.autocontroller.util.ObjectConverter;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/driver")
public class DriverRestController {
    private final ObjectConverter objectConverter;
    private final DriverRepository driverRepository;
    private final ManagerService managerService;

    @Autowired
    public DriverRestController(ObjectConverter objectConverter,
                                DriverRepository driverRepository, ManagerService managerService) {
        this.managerService = managerService;
        this.objectConverter = objectConverter;
        this.driverRepository = driverRepository;
    }

    @GetMapping
    public List<DriverDTO> index() {
        return driverRepository.findAllByEnterprise_ManagersIn(Collections.singleton(managerService.getManager()))
                               .stream()
                               .map(objectConverter::convertToDriverDTO)
                               .toList();
    }

}
