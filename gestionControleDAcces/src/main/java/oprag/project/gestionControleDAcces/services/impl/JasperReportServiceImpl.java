package oprag.project.gestionControleDAcces.services.impl;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import oprag.project.gestionControleDAcces.dto.CertificatControlDAO;
import oprag.project.gestionControleDAcces.dto.InspectionCardData;
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

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    public ResponseEntity<byte[]> exportReport(String reportFormat, Integer certificatControlId) throws IOException, JRException {
        String userHome = System.getProperty("user.home");
        Path downloadsPath = Paths.get(userHome, "Downloads");

        var certificatControl= this.certificationControlRepository.findById(certificatControlId).map(CertificatControlDAO::fromEntity).orElseThrow(()-> new EntityNotFoundException("aucun certificatControl pour ce id fourni"));
        var directeurDGMG=this.utilisateurRepository.findUtilisateurByInspectionNomAndRole(certificatControl.getUtilisateur().getInspection().getNom(),UserRole.INSPECTEUR_PRINCIPAL).map(UtilisateurDAO::fromEntity).orElseThrow(()-> new EntityNotFoundException("Le directeur DGMG n'est pas trouvé"));
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
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
                Collections.singleton(reportData)
        );

        // Compiler les deux rapports
        JasperReport reportRecto = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/ficheInspectionFace.jrxml")
        );
        JasperReport reportVerso = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/ficheInspectionDerriere.jrxml")
        );

        JRBeanCollectionDataSource datasourceSecondPage = new JRBeanCollectionDataSource(
                Collections.singleton(reportData)
        );
        // Remplir les rapports
        JasperPrint printRecto = JasperFillManager.fillReport(reportRecto, new HashMap<>(), dataSource);
        JasperPrint printVerso = JasperFillManager.fillReport(reportVerso, new HashMap<>(), datasourceSecondPage);

        List<JasperPrint> prints = Arrays.asList(printRecto, printVerso);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(prints));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        exporter.exportReport();

        // Préparer la réponse HTTP
        byte[] bytes = baos.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "ficheInspection.pdf");
        headers.setContentLength(bytes.length);

        return ResponseEntity.ok().headers(headers).body(bytes);
    }


    @Override
    public ResponseEntity<byte[]> exportReport(InspectionCardData inspectionCardData) throws JRException, IOException {
        // Préparer les données
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
                Collections.singleton(inspectionCardData)
        );

        // Compiler les deux rapports
        JasperReport reportRecto = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/carteInspectionRecto.jrxml")
        );
        JasperReport reportVerso = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/carteInspectionVerso.jrxml")
        );

        JRDataSource emptyDataSource = new JREmptyDataSource();

        // Remplir les rapports
        JasperPrint printRecto = JasperFillManager.fillReport(reportRecto, new HashMap<>(), dataSource);
        JasperPrint printVerso = JasperFillManager.fillReport(reportVerso, new HashMap<>(), emptyDataSource);

        List<JasperPrint> prints = Arrays.asList(printRecto, printVerso);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(prints));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        exporter.exportReport();

        // Préparer la réponse HTTP
        byte[] bytes = baos.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "CarteInspection.pdf");
        headers.setContentLength(bytes.length);

        return ResponseEntity.ok().headers(headers).body(bytes);
    }

}
