package oprag.project.gestionControleDAcces.services;

import oprag.project.gestionControleDAcces.dto.BadgeDAO;

import java.util.List;

public interface BadgeService {
    BadgeDAO save(BadgeDAO badgeDAO);
    BadgeDAO findByNumero(String numero);
    List<BadgeDAO> findAll();
    void delete(Integer id);
}
