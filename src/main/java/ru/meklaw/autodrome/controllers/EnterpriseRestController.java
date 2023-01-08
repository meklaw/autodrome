package ru.meklaw.autodrome.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.meklaw.autodrome.dto.EnterpriseDTO;
import ru.meklaw.autodrome.models.Enterprise;
import ru.meklaw.autodrome.service.EnterprisesService;

import java.util.List;

@RestController
@RequestMapping("/api/enterprises")
public class EnterpriseRestController {
    private final EnterprisesService enterprisesService;
    private final ModelMapper modelMapper;

    public EnterpriseRestController(EnterprisesService enterprisesService, ModelMapper modelMapper) {
        this.enterprisesService = enterprisesService;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public List<EnterpriseDTO> index() {
        return enterprisesService.findAllByManager()
                .stream()
                .map(this::convertToEnterpriseDTO)
                .toList();
    }

    private EnterpriseDTO convertToEnterpriseDTO(Enterprise enterprise) {
        return modelMapper.map(enterprise, EnterpriseDTO.class);
    }

}