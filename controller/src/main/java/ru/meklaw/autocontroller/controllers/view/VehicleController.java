package ru.meklaw.autocontroller.controllers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autocontroller.controllers.rest.EnterpriseRestController;
import ru.meklaw.autocontroller.controllers.rest.TripGpsRestController;
import ru.meklaw.autocontroller.controllers.rest.VehicleRestController;
import ru.meklaw.autocontroller.dto.VehicleDTO;
import ru.meklaw.autocontroller.service.VehicleBrandsService;

import java.time.ZoneId;


@Controller
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleBrandsService vehicleBrandsService;
    private final VehicleRestController vehicleRestController;
    private final EnterpriseRestController enterpriseRestController;
    private final TripGpsRestController tripGpsRestController;
    private final ZoneId localZoneId;


    @Autowired
    public VehicleController(VehicleBrandsService vehicleBrandsService,
                             VehicleRestController vehicleRestController,
                             EnterpriseRestController enterpriseRestController,
                             TripGpsRestController tripGpsRestController, ZoneId localZoneId) {
        this.vehicleBrandsService = vehicleBrandsService;
        this.vehicleRestController = vehicleRestController;
        this.enterpriseRestController = enterpriseRestController;
        this.tripGpsRestController = tripGpsRestController;
        this.localZoneId = localZoneId;
    }

    @GetMapping
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size,
                        @RequestParam(defaultValue = "-1") long enterprise_id, Model model) {
        model.addAttribute("vehicles", vehicleRestController.index(page, size, enterprise_id));

        if (enterprise_id != -1) {
            model.addAttribute("enterprise", enterpriseRestController.findById(enterprise_id));
        }

        return "/vehicles/index";
    }

    @GetMapping("/create")
    public String create(@RequestParam(defaultValue = "-1") long enterprise_id,
                         Model model) {
        VehicleDTO dto = new VehicleDTO();

        if (enterprise_id != -1) {
            dto.setEnterpriseId(enterprise_id);
        } else {
            model.addAttribute("enterprises", enterpriseRestController.index());
        }

        model.addAttribute("vehicle", dto);
        model.addAttribute("brands", vehicleBrandsService.findAll());

        return "/vehicles/create";
    }

    @PostMapping
    public String save(VehicleDTO vehicle) {
        vehicleRestController.create(vehicle);

        return "redirect:/vehicles";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable long id, Model model) {
        VehicleDTO vehicleDTO = vehicleRestController.findById(id);

        model.addAttribute("vehicle", vehicleDTO);
        model.addAttribute("trips",
                tripGpsRestController.indexAllTrips(id)
                                     .stream()
                                     .peek(tripDTO -> tripDTO.changeTimeWithZone(localZoneId)));

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

