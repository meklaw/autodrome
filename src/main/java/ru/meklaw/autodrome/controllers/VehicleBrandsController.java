package ru.meklaw.autodrome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.models.VehicleBrand;
import ru.meklaw.autodrome.models.VehicleType;
import ru.meklaw.autodrome.service.VehicleBrandsService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/brands")
public class VehicleBrandsController {
    private final VehicleBrandsService vehicleBrandsService;

    @Autowired
    public VehicleBrandsController(VehicleBrandsService vehicleBrandsService) {
        this.vehicleBrandsService = vehicleBrandsService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("brands", vehicleBrandsService.findAll());
        return "/brands/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        // Create the list of vehicle types
        List<String> vehicleTypes = Arrays.stream(VehicleType.values()).map(Objects::toString).toList();
        // Add the list to the model
        model.addAttribute("vehicleTypes", vehicleTypes);
        model.addAttribute("brand", new VehicleBrand());
        // Return the view template
        return "brands/create";
    }


    @PostMapping
    public String save(VehicleBrand brand) {
        vehicleBrandsService.create(brand);
        return "redirect:/brands";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable long id, Model model) {
        model.addAttribute("brand", vehicleBrandsService.findById(id).orElseThrow());
        return "/brands/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        // Create the list of vehicle types
        List<String> vehicleTypes = Arrays.stream(VehicleType.values()).map(Objects::toString).toList();

        // Add the list to the model
        model.addAttribute("vehicleTypes", vehicleTypes);
        model.addAttribute("brand", vehicleBrandsService.findById(id).orElseThrow());
        return "/brands/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, VehicleBrand brand) {
        vehicleBrandsService.update(id, brand);
        return "redirect:/brands";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        vehicleBrandsService.delete(id);
        return "redirect:/brands";
    }
}
