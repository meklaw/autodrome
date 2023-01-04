package ru.meklaw.autodrome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.models.Vehicle;
import ru.meklaw.autodrome.security.ManagerDetails;
import ru.meklaw.autodrome.service.VehicleBrandsService;
import ru.meklaw.autodrome.service.VehiclesService;


@Controller
@RequestMapping("/vehicles")
public class VehiclesController {
    private final VehiclesService vehiclesService;
    private final VehicleBrandsService vehicleBrandsService;

    @Autowired
    public VehiclesController(VehiclesService vehiclesService, VehicleBrandsService vehicleBrandsService) {
        this.vehiclesService = vehiclesService;
        this.vehicleBrandsService = vehicleBrandsService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("vehicles", vehiclesService.findAll());

        return "/vehicles/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("brands", vehicleBrandsService.findAll());
        return "/vehicles/create";
    }

    @PostMapping
    public String save(Vehicle vehicle) {
        vehiclesService.create(vehicle);
        return "redirect:/vehicles";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable int id, Model model) {
        model.addAttribute("vehicle", vehiclesService.findById(id).get());
        return "/vehicles/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("vehicle", vehiclesService.findById(id).get());
        model.addAttribute("brands", vehicleBrandsService.findAll());
        return "/vehicles/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id, Vehicle vehicle) {
        vehiclesService.update(vehicle);
        return "redirect:/vehicles";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        vehiclesService.delete(id);
        return "redirect:/vehicles";
    }

}

