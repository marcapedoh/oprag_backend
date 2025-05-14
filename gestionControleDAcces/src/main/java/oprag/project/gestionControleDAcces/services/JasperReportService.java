package oprag.project.gestionControleDAcces.services;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface JasperReportService {
    public String exportReport(String reportFormat,Integer certificatControlId) throws FileNotFoundException, JRException;
}
