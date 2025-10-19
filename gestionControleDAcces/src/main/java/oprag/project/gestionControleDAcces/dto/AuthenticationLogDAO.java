package oprag.project.gestionControleDAcces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oprag.project.gestionControleDAcces.models.AuthenticationLog;
import oprag.project.gestionControleDAcces.models.Utilisateur;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationLogDAO {
    private Integer id;
    private UtilisateurDAO utilisateur;
    private String localisation;
    private LocalDateTime date;

    public static AuthenticationLogDAO fromEntity(AuthenticationLog authenticationLog) {
        if(authenticationLog==null){
            return null;
        }
        return AuthenticationLogDAO.builder()
                .id(authenticationLog.getId())
                .utilisateur(UtilisateurDAO.fromEntity(authenticationLog.getUtilisateur()))
                .localisation(authenticationLog.getLocalisation())
                .date(authenticationLog.getDate())
                .build();
    }

    public static AuthenticationLog toEntity(AuthenticationLogDAO authenticationLogDAO) {
        if(authenticationLogDAO==null){
            return null;
        }

        AuthenticationLog authenticationLog= new   AuthenticationLog();
        authenticationLog.setId(authenticationLogDAO.getId());
        authenticationLog.setDate(authenticationLogDAO.getDate());
        authenticationLog.setUtilisateur(UtilisateurDAO.toEntity(authenticationLogDAO.getUtilisateur()));
        authenticationLog.setLocalisation(authenticationLogDAO.getLocalisation());
        return authenticationLog;
    }
}
