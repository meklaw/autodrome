package ru.meklaw.autodrome.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.meklaw.autodrome.dto.GenerateReport;
import ru.meklaw.autodrome.dto.Report;
import ru.meklaw.autodrome.service.ReportService;

@RestController
@RequestMapping("/api/report")
public class ReportRestController {
    private final ReportService reportService;

    @Autowired
    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public Report generateReport(@RequestBody GenerateReport generateReport) {
        return reportService.generateReport(generateReport);
    }
}
