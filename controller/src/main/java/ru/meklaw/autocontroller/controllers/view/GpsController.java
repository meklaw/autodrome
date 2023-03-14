package ru.meklaw.autocontroller.controllers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.meklaw.autocontroller.controllers.rest.TripGpsRestController;
import ru.meklaw.autocontroller.dto.TripDTO;

import java.time.ZoneId;

@Controller
@RequestMapping("/gps")
public class GpsController {
    private final TripGpsRestController tripGpsRestController;
    private final ZoneId localZoneId;

    @Autowired
    public GpsController(TripGpsRestController tripGpsRestController, ZoneId localZoneId) {
        this.tripGpsRestController = tripGpsRestController;
        this.localZoneId = localZoneId;
    }

    @GetMapping("/trip/{tripId}")
    public String findById(@PathVariable long tripId, Model model) {
        TripDTO tripDTO = tripGpsRestController.indexTrip(tripId);
        tripDTO.changeTimeWithZone(localZoneId);
        model.addAttribute("trip", tripDTO);
        model.addAttribute("mapUrl", tripGpsRestController.indexTripMap(tripId));
        return "/gps/trip/view";
    }
}
