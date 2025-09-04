package oprag.project.gestionControleDAcces.services;

import net.sf.jasperreports.engine.JRException;
import oprag.project.gestionControleDAcces.dto.InspectionCardData;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface JasperReportService {
    public ResponseEntity<byte[]> exportReport(String reportFormat, Integer certificatControlId) throws IOException, JRException;

    ResponseEntity<byte[]> exportReport(InspectionCardData inspectionCardData) throws JRException, IOException;
}
