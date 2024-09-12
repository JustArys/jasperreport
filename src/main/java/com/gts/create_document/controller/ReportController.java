package com.gts.create_document.controller;



import com.gts.create_document.service.JReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private JReportService jReportService;

    @GetMapping("/vocation/{id}")
    public void generateReport(@PathVariable Long id, HttpServletResponse response) throws IOException {
        try {
            jReportService.exportJasperReport(id, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating report: " + e.getMessage());
        }
    }
}
