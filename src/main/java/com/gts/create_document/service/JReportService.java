package com.gts.create_document.service;


import com.gts.create_document.dto.Vocation;
import com.gts.create_document.model.Employee;
import com.gts.create_document.model.EmployeeLeave;
import com.gts.create_document.repository.EmployeeLeaveRepository;
import com.gts.create_document.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JReportService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeLeaveRepository employeeLeaveRepository;

    @Transactional
    public void exportJasperReport(Long leaveId, HttpServletResponse response) throws JRException, IOException {
        // Fetch data
        EmployeeLeave leave = employeeLeaveRepository.findById(leaveId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid leave ID"));

        Employee employee = employeeRepository.findById(leave.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));

        Employee boss = employeeRepository.findById(leave.getBossId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid boss ID"));

        Vocation vocation = new Vocation(employee.getName(), employee.getPosition(), leave.getStartDate(), leave.getEndDate(), boss.getName(), boss.getPosition());
        System.out.println(vocation);
        // Prepare data for report
        List<Vocation> vocationData = List.of(vocation);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vocationData);

        // Load and compile report
        InputStream reportStream = new ClassPathResource("vocation.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Set parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("employeeName", vocation.getEmployeeName());
        parameters.put("employeePosition", vocation.getEmployeePosition());
        parameters.put("startDate", vocation.getStartDate());
        parameters.put("endDate", vocation.getEndDate());
        parameters.put("bossName", vocation.getBossName());
        parameters.put("bossPosition", vocation.getBossPosition());

        // Fill report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export to PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=leave_report.pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
