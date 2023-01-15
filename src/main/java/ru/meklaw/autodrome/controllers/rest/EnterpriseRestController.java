package ru.meklaw.autodrome.controllers.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.dto.EnterpriseDTO;
import ru.meklaw.autodrome.dto.FillEnterprisesDTO;
import ru.meklaw.autodrome.models.Enterprise;
import ru.meklaw.autodrome.service.EnterprisesService;

import java.util.List;

@RestController
@RequestMapping("/api/enterprises")
public class EnterpriseRestController {
    private final EnterprisesService enterprisesService;
    private final ModelMapper modelMapper;

    @Autowired
    public EnterpriseRestController(EnterprisesService enterprisesService, ModelMapper modelMapper) {
        this.enterprisesService = enterprisesService;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public List<EnterpriseDTO> index() {
        return enterprisesService.findAll()
                                 .stream()
                                 .map(this::convertToEnterpriseDTO)
                                 .toList();
    }

    @PostMapping("/init")
    public List<EnterpriseDTO> init(@RequestBody List<FillEnterprisesDTO> enterprises) {
        return enterprisesService.init(enterprises)
                                 .stream()
                                 .map(this::convertToEnterpriseDTO)
                                 .toList();
    }

    private EnterpriseDTO convertToEnterpriseDTO(Enterprise enterprise) {
        return modelMapper.map(enterprise, EnterpriseDTO.class);
    }

}