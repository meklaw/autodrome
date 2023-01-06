package ru.meklaw.autodrome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.models.Vehicle;
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
    public String findById(@PathVariable long id, Model model) {
        model.addAttribute("vehicle", vehiclesService.findById(id).orElseThrow());
        return "/vehicles/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("vehicle", vehiclesService.findById(id).orElseThrow());
        model.addAttribute("brands", vehicleBrandsService.findAll());
        return "/vehicles/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable long id, Vehicle vehicle) {
        vehiclesService.update(id, vehicle);
        return "redirect:/vehicles";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        vehiclesService.delete(id);
        return "redirect:/vehicles";
    }

}

