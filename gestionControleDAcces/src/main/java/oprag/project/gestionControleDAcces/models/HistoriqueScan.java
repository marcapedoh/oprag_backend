package oprag.project.gestionControleDAcces.models;


import jakarta.persistence.Column;
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
public class HistoriqueScan extends  AbstractEntity{
    @ManyToOne
    @JoinColumn(name = "utilisateurId")
    private Utilisateur utilisateur;
    @Column(name = "dateScan")
    private LocalDateTime date;

}
