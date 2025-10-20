package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.BadgeDAO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BadgeService {
    BadgeDAO save(BadgeDAO badgeDAO);
    BadgeDAO findByNumero(String numero);
    Page<BadgeDAO> findAll(int page, int size);
    List<BadgeDAO> findAllCertificatCreationDateAndInspectionId(LocalDate dateDebut,LocalDate dateFin, Integer inspectionId);

    List<BadgeDAO> findAllCertificatCreationDate(LocalDate dateDebut,LocalDate dateFin);
    ResponseEntity<byte[]> getQrCode(String numero);
    List<Object> countAllPerDay();
    long numberOfBadges();
    Map<String, Object> numberOfBadgePerInspection();

    List<Object> countAllPerIntervalDays(LocalDate startDate, LocalDate endDate);
    List<BadgeDAO> findAllPerInspecteurId(Integer id);
    void delete(Integer id);
}
