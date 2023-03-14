package ru.meklaw.autocontroller.controllers.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.meklaw.autocontroller.controllers.rest.EnterpriseRestController;

@Controller
@RequestMapping("/enterprises")
public class EnterpriseController {

    private final EnterpriseRestController enterpriseRestController;

    public EnterpriseController(EnterpriseRestController enterpriseRestController) {
        this.enterpriseRestController = enterpriseRestController;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("enterprises", enterpriseRestController.index());

        return "enterprise/index";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable long id, Model model) {
        model.addAttribute("enterprise", enterpriseRestController.findById(id));

        return "enterprise/view";
    }
}
