package oprag.project.gestionControleDAcces.repository;

import oprag.project.gestionControleDAcces.models.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge,Integer> {
    Optional<Badge> findBadgeByNumero(String numero);
    List<Badge> findBadgeByInspecteurId(Integer id);
    Optional<Badge> findTopByInspecteurIdOrderByDateCreationDesc(Integer inspecteurId);

}
