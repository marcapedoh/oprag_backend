package oprag.project.gestionControleDAcces.services;

import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface JasperReportService {
    public ResponseEntity<byte[]> exportReport(String reportFormat, Integer certificatControlId) throws IOException, JRException;
}
