package ru.meklaw.autodrome.controllers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.meklaw.autodrome.controllers.rest.TripGpsRestController;
import ru.meklaw.autodrome.dto.TripDTO;

import java.time.ZoneId;

@Controller
@RequestMapping("/gps")
public class GpsController {
    private final TripGpsRestController tripGpsRestController;

    @Autowired
    public GpsController(TripGpsRestController tripGpsRestController) {
        this.tripGpsRestController = tripGpsRestController;
    }

    @GetMapping("/trip/{tripId}")
    public String findById(@PathVariable long tripId, Model model) {
        TripDTO tripDTO = tripGpsRestController.indexTrip(tripId);
        tripDTO.changeTimeWithZone(ZoneId.of("Europe/Moscow"));
        model.addAttribute("trip", tripDTO);
        model.addAttribute("mapUrl", tripGpsRestController.indexTripMap(tripId));
        return "/gps/trip/view";
    }
}
