package ru.meklaw.autodrome.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.dto.EnterpriseDTO;
import ru.meklaw.autodrome.dto.FillEnterprisesDTO;
import ru.meklaw.autodrome.service.EnterprisesService;
import ru.meklaw.autodrome.util.ObjectConverter;

import java.util.List;

@RestController
@RequestMapping("/api/enterprises")
public class EnterpriseRestController {
    private final EnterprisesService enterprisesService;
    private final ObjectConverter objectConverter;

    @Autowired
    public EnterpriseRestController(EnterprisesService enterprisesService, ObjectConverter objectConverter) {
        this.enterprisesService = enterprisesService;
        this.objectConverter = objectConverter;
    }

    @GetMapping
    public List<EnterpriseDTO> index() {
        return enterprisesService.findAll()
                                 .stream()
                                 .map(objectConverter::convertToEnterpriseDTO)
                                 .toList();
    }

    @GetMapping("/{id}")
    public EnterpriseDTO findById(@PathVariable long id) {
        return objectConverter.convertToEnterpriseDTO(enterprisesService.findById(id));
    }

    @PostMapping("/init")
    public List<EnterpriseDTO> init(@RequestBody List<FillEnterprisesDTO> enterprises) {
        return enterprisesService.init(enterprises)
                                 .stream()
                                 .map(objectConverter::convertToEnterpriseDTO)
                                 .toList();
    }

}