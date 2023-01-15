package ru.meklaw.autodrome.controllers.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.meklaw.autodrome.controllers.rest.EnterpriseRestController;

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
}
