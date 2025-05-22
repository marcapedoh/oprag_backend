package oprag.project.gestionControleDAcces.controller;


import net.sf.jasperreports.engine.JRException;
import oprag.project.gestionControleDAcces.controller.API.JasperReportAPI;
import oprag.project.gestionControleDAcces.services.JasperReportService;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
public class JasperReportController implements JasperReportAPI {

    private JasperReportService jasperReportService;

    public JasperReportController(JasperReportService jasperReportService) {
        this.jasperReportService = jasperReportService;
    }

    @Override
    public String exportReport(String reportFormat, Integer certificatControlId) throws FileNotFoundException, JRException {
        return this.jasperReportService.exportReport(reportFormat, certificatControlId);
    }
}
