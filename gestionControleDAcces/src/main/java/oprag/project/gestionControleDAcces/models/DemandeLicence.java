package oprag.project.gestionControleDAcces.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
public class DemandeLicence extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    private TypeDemande typeDemande;
    @Enumerated(EnumType.STRING)
    private StatutDemande statutDemande;
    @Column(name = "dateDemande")
    private LocalDate dateSoumission;
    @Column(name = "commentaire",length = 2500000)
    private String commentaire;
    @ManyToOne
    @JoinColumn(name = "licenceId")
    private Licence licence;
    @ManyToOne
    @JoinColumn(name = "utilisateurId")
    private Utilisateur utilisateur;
}
