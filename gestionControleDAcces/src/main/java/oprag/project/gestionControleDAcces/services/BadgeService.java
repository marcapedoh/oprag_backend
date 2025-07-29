package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.BadgeDAO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BadgeService {
    BadgeDAO save(BadgeDAO badgeDAO);
    BadgeDAO findByNumero(String numero);
    List<BadgeDAO> findAll();
    ResponseEntity<byte[]> getQrCode(String numero);
    List<Object> countAllPerDay();
    long numberOfBadges();
    Map<String, Object> numberOfBadgePerInspection();

    List<Object> countAllPerIntervalDays(LocalDate startDate, LocalDate endDate);
    List<BadgeDAO> findAllPerInspecteurId(Integer id);
    void delete(Integer id);
}
