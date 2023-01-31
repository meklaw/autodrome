package ru.meklaw.autodrome.controllers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.meklaw.autodrome.controllers.rest.TripGpsRestController;

@Controller
@RequestMapping("/gps")
public class GpsController {
    private final TripGpsRestController tripGpsRestController;

    @Autowired
    public GpsController(TripGpsRestController tripGpsRestController) {
        this.tripGpsRestController = tripGpsRestController;
    }

    @GetMapping("/trip/{id}")
    public String findById(@PathVariable long id, Model model) {
        model.addAttribute("imageUrl", tripGpsRestController.indexTripMap(id));
        return "/gps/trip/view";
    }
}
