package ru.meklaw.autodrome.controllers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.meklaw.autodrome.models.Person;
import ru.meklaw.autodrome.service.EnterprisesService;

@Controller
@RequestMapping("/enterprises")
public class EnterpriseController {

    private final EnterprisesService enterpriseService;

    @Autowired
    public EnterpriseController(EnterprisesService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @GetMapping
    public String index(Model model) {
        var authentication = SecurityContextHolder.getContext()
                                                  .getAuthentication();
        Person person = (Person) authentication.getPrincipal();

        if (person.getManager() != null)
            model.addAttribute("enterprises", enterpriseService.findAllByManager());
        else
            model.addAttribute("enterprises", enterpriseService.findAll());

        return "enterprise/index";
    }
}
