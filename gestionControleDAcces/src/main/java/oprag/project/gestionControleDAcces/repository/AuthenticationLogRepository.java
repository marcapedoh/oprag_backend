package oprag.project.gestionControleDAcces.repository;

import oprag.project.gestionControleDAcces.dto.AuthenticationLogDAO;
import oprag.project.gestionControleDAcces.models.AuthenticationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface AuthenticationLogRepository extends JpaRepository<AuthenticationLog,Integer> {
    List<AuthenticationLog> findAllByUtilisateurId(Integer utilisateurId);

    List<AuthenticationLog> findByDateBetween(LocalDateTime start, LocalDateTime end);


}
