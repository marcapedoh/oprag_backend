package oprag.project.gestionControleDAcces.models;

import jakarta.persistence.*;
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
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "codeInspection", nullable = false, unique = true)
    private String codeInspection;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Enumerated(EnumType.STRING)
    @Column(name = "typeControl",nullable = false)
    private TypeControl type;

    @Column(name = "logo",nullable = false)
    private String logo;

    @OneToMany(mappedBy = "inspection")
    private List<Utilisateur> inspecteurs;
}
