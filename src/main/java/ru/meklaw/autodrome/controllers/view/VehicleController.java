package ru.meklaw.autodrome.controllers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.controllers.rest.EnterpriseRestController;
import ru.meklaw.autodrome.controllers.rest.VehicleRestController;
import ru.meklaw.autodrome.dto.VehicleDTO;
import ru.meklaw.autodrome.models.Vehicle;
import ru.meklaw.autodrome.service.VehicleBrandsService;


@Controller
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleBrandsService vehicleBrandsService;
    private final VehicleRestController vehicleRestController;
    private final EnterpriseRestController enterpriseRestController;

    @Autowired
    public VehicleController(VehicleBrandsService vehicleBrandsService, VehicleRestController vehicleRestController, EnterpriseRestController enterpriseRestController) {
        this.vehicleBrandsService = vehicleBrandsService;
        this.vehicleRestController = vehicleRestController;
        this.enterpriseRestController = enterpriseRestController;
    }

    @GetMapping
    public String index(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "-1") long enterprise_id, Model model) {

        model.addAttribute("vehicles", vehicleRestController.index(page, size, enterprise_id));

        return "/vehicles/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("brands", vehicleBrandsService.findAll());
        model.addAttribute("enterprises", enterpriseRestController.index());

        return "/vehicles/create";
    }

    @PostMapping
    public String save(VehicleDTO vehicle) {
        vehicleRestController.create(vehicle);

        return "redirect:/vehicles";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable long id, Model model) {
        model.addAttribute("vehicle", vehicleRestController.findById(id));

        return "/vehicles/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("vehicle", vehicleRestController.findById(id));
        model.addAttribute("brands", vehicleBrandsService.findAll());
        model.addAttribute("enterprises", enterpriseRestController.index());

        return "/vehicles/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, VehicleDTO vehicle) {
        vehicleRestController.update(id, vehicle);

        return "redirect:/vehicles";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        vehicleRestController.delete(id);

        return "redirect:/vehicles";
    }
}

