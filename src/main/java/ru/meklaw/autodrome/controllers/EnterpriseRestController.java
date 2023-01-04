package ru.meklaw.autodrome.controllers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.meklaw.autodrome.dto.EnterpriseDTO;
import ru.meklaw.autodrome.models.Driver;
import ru.meklaw.autodrome.models.Enterprise;
import ru.meklaw.autodrome.models.Vehicle;
import ru.meklaw.autodrome.repositories.ManagerRepository;
import ru.meklaw.autodrome.security.ManagerDetails;
import ru.meklaw.autodrome.service.EnterprisesService;
import ru.meklaw.autodrome.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping("/api/enterprises")
public class EnterpriseRestController {
    private final EnterprisesService enterprisesService;
    private final ManagerService managerService;

    public EnterpriseRestController(EnterprisesService enterprisesService, ManagerService managerService) {
        this.enterprisesService = enterprisesService;
        this.managerService = managerService;
    }


    @GetMapping
    public List<EnterpriseDTO> index() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        ManagerDetails managerDetails = (ManagerDetails) authentication.getPrincipal();

        return managerService.findById(managerDetails.getManager().getId())
                .getEnterprises()
                .stream()
                .map(this::convertToEnterpriseDTO)
                .toList();
    }

    private EnterpriseDTO convertToEnterpriseDTO(Enterprise enterprise) {
        EnterpriseDTO dto = new EnterpriseDTO();

        dto.setId(enterprise.getId());
        dto.setName(enterprise.getName());
        dto.setCity(enterprise.getCity());
        dto.setFounded(enterprise.getFounded());
        dto.setVehicles(enterprise.getVehicles().stream().map(Vehicle::getId).toList());
        dto.setDrivers(enterprise.getDrivers().stream().map(Driver::getId).toList());

        return dto;
    }

}