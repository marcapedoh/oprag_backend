package oprag.project.gestionControleDAcces.services.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;
import oprag.project.gestionControleDAcces.dto.ReportData;
import oprag.project.gestionControleDAcces.dto.UtilisateurDAO;
import oprag.project.gestionControleDAcces.exception.EntityNotFoundException;
import oprag.project.gestionControleDAcces.models.EssaiFonctionnement;
import oprag.project.gestionControleDAcces.models.UserRole;
import oprag.project.gestionControleDAcces.repository.CertificatControlRepository;
import oprag.project.gestionControleDAcces.repository.UtilisateurRepository;
import oprag.project.gestionControleDAcces.services.JasperReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JasperReportServiceImpl implements JasperReportService {

    private CertificatControlRepository certificationControlRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    public JasperReportServiceImpl(CertificatControlRepository certificationControlRepository, UtilisateurRepository utilisateurRepository) {
        this.certificationControlRepository = certificationControlRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public String getEssaisConcatenes(List<EssaiFonctionnement> essaiFonctionnementList) {
        return essaiFonctionnementList == null ? "" :
                essaiFonctionnementList.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
    }

    @Override
    public ResponseEntity<byte[]> exportReport(String reportFormat, Integer certificatControlId) throws FileNotFoundException, JRException {
        String userHome = System.getProperty("user.home");
        Path downloadsPath = Paths.get(userHome, "Downloads");
        String outputPath="";
        var certificatControl= this.certificationControlRepository.findById(certificatControlId).map(CertificatControlDAO::fromEntity).orElseThrow(()-> new EntityNotFoundException("aucun certificatControl pour ce id fourni"));
        var directeurDGMG=this.utilisateurRepository.findUtilisateurByInspectionNomAndRole(certificatControl.getUtilisateur().getInspection().getNom(),UserRole.DGMG).map(UtilisateurDAO::fromEntity).orElseThrow(()-> new EntityNotFoundException("Le directeur DGMG n'est pas trouvé"));
        ZoneId zoneId = ZoneId.systemDefault(); // ou ZoneId.of("Europe/Paris") selon le besoin
        LocalDate localDate = this.certificationControlRepository.findById(certificatControlId).get().getDateCreation().atZone(zoneId).toLocalDate();
        String essaisConcatenes=getEssaisConcatenes(certificatControl.getEssaiFonctionnementList());
        ReportData reportData=ReportData.builder()
                .utilisateur(directeurDGMG)
                .dateCertificat(localDate)
                .essaisConcatenes(essaisConcatenes)
                .inspection(directeurDGMG.getInspection())
                .certificatControl(certificatControl)
                .build();
//        File file= ResourceUtils.getFile("classpath:inspectionFiche.jrxml");
//        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        InputStream jrxmlStream = getClass().getResourceAsStream("/inspectionFiche.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

        JRBeanCollectionDataSource dataSource= new JRBeanCollectionDataSource(Collections.singleton(reportData));
        Map<String,Object> parameters= new HashMap<>();
        parameters.put("CreatedBy","MarcAPEDOHKossiKekeli");
        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,parameters,dataSource);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(reportFormat.equalsIgnoreCase("pdf")){
             //outputPath = downloadsPath.resolve("inspectionFiche.pdf").toString();
            //JasperExportManager.exportReportToPdfFile(jasperPrint,outputPath);
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            byte[] bytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "inspectionFiche.pdf");
            headers.setContentLength(bytes.length);

            return ResponseEntity.ok().headers(headers).body(bytes);
        }
//        if(reportFormat.equalsIgnoreCase("html")){
//             outputPath = downloadsPath.resolve("inspectionFiche.html").toString();
//            JasperExportManager.exportReportToHtmlFile(jasperPrint,outputPath);
//        }
        throw new IllegalArgumentException("Format non supporté : " + reportFormat);
    }
}
