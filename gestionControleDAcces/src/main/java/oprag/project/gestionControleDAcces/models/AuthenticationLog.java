package oprag.project.gestionControleDAcces.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
public class AuthenticationLog extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name = "utilisateurId")
    private Utilisateur utilisateur;
    private String localisation;
    private LocalDateTime date;

}
