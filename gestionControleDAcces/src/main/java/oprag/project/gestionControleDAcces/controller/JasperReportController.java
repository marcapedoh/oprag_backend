package oprag.project.gestionControleDAcces.controller;


import net.sf.jasperreports.engine.JRException;
import oprag.project.gestionControleDAcces.controller.API.JasperReportAPI;
import oprag.project.gestionControleDAcces.services.JasperReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
public class JasperReportController implements JasperReportAPI {

    private JasperReportService jasperReportService;

    public JasperReportController(JasperReportService jasperReportService) {
        this.jasperReportService = jasperReportService;
    }

    @Override
    public ResponseEntity<byte[]> exportReport(String reportFormat, Integer certificatControlId) throws IOException, JRException {
        return this.jasperReportService.exportReport(reportFormat, certificatControlId);
    }

    @Override
    public ResponseEntity<byte[]> exportReportForQrCode(String reportFormat, Integer certificatControlId) throws IOException, JRException {
        return this.jasperReportService.exportReport(reportFormat, certificatControlId);
    }
}
