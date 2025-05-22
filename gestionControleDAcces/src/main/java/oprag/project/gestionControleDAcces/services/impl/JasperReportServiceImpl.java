package oprag.project.gestionControleDAcces.services.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;
import oprag.project.gestionControleDAcces.dto.ReportData;
import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import oprag.project.gestionControleDAcces.exception.EntityNotFoundException;
import oprag.project.gestionControleDAcces.models.UserRole;
import oprag.project.gestionControleDAcces.repository.CertificatControlRepository;
import oprag.project.gestionControleDAcces.repository.UtilisateurRepository;
import oprag.project.gestionControleDAcces.services.JasperReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class JasperReportServiceImpl implements JasperReportService {

    private CertificatControlRepository certificationControlRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    public JasperReportServiceImpl(CertificatControlRepository certificationControlRepository, UtilisateurRepository utilisateurRepository) {
        this.certificationControlRepository = certificationControlRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public String exportReport(String reportFormat, Integer certificatControlId) throws FileNotFoundException, JRException {
        String userHome = System.getProperty("user.home");
        Path downloadsPath = Paths.get(userHome, "Downloads");
        var certificatControl= this.certificationControlRepository.findById(certificatControlId).map(CertificatControlDAO::fromEntity).orElseThrow(()-> new EntityNotFoundException("aucun certificatControl pour ce id fourni"));
        var directeurDGMG=this.utilisateurRepository.findUtilisateurByInspectionNomAndRole(certificatControl.getUtilisateur().getInspection().getNom(),UserRole.DGMG).map(UtilisateurDAO::fromEntity).orElseThrow(()-> new EntityNotFoundException("Le directeur DGMG n'est pas trouvé"));
        ZoneId zoneId = ZoneId.systemDefault(); // ou ZoneId.of("Europe/Paris") selon le besoin
        LocalDate localDate = this.certificationControlRepository.findById(certificatControlId).get().getDateCreation().atZone(zoneId).toLocalDate();
        ReportData reportData=ReportData.builder()
                .utilisateur(directeurDGMG)
                .dateCertificat(localDate)
                .inspection(directeurDGMG.getInspection())
                .certificatControl(certificatControl)
                .build();
        File file= ResourceUtils.getFile("classpath:inspectionFiche.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource= new JRBeanCollectionDataSource(Collections.singleton(reportData));
        Map<String,Object> parameters= new HashMap<>();
        parameters.put("CreatedBy","MarcAPEDOHKossiKekeli");
        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,parameters,dataSource);
        if(reportFormat.equalsIgnoreCase("pdf")){
            String outputPath = downloadsPath.resolve("inspectionFiche.pdf").toString();
            JasperExportManager.exportReportToPdfFile(jasperPrint,outputPath);
        }
        if(reportFormat.equalsIgnoreCase("html")){
            String outputPath = downloadsPath.resolve("inspectionFiche.html").toString();
            JasperExportManager.exportReportToHtmlFile(jasperPrint,outputPath);
        }
        return "";
    }
}
