package ru.meklaw.autodrome.controllers.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.meklaw.autodrome.controllers.rest.ReportRestController;
import ru.meklaw.autodrome.dto.GenerateReport;
import ru.meklaw.autodrome.dto.Period;
import ru.meklaw.autodrome.dto.ReportType;

@Controller
@RequestMapping("/report")
public class ReportController {
    private final ReportRestController reportRestController;

    public ReportController(ReportRestController reportRestController) {
        this.reportRestController = reportRestController;
    }

    @GetMapping
    public String getFinder(Model model) {
        model.addAttribute("generateReport", new GenerateReport());
        model.addAttribute("reportTypes", ReportType.values());
        model.addAttribute("reportPeriods", Period.values());

        return "/report/finder";
    }

    @PostMapping
    public String generateReport(GenerateReport generateReport, Model model) {
        model.addAttribute("report", reportRestController.generateReport(generateReport));

        return "/report/view";
    }
}
