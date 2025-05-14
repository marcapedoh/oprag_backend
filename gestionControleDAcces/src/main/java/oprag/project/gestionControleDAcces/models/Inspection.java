package oprag.project.gestionControleDAcces.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
public class Inspection extends AbstractEntity {
    @Column(name = "nom", nullable = false,unique = true)
    private String nom;
    @Column(name = "logo",nullable = false)
    private String logo;

    @OneToMany(mappedBy = "inspection")
    private List<Utilisateur> inspecteurs;
}
