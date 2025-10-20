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
public class Licence extends AbstractEntity{
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LicenceStatus status;
    @Column(name = "gracDays")
    private Integer graceDays;
    @Column(name = "numeroLicence")
    private String numerolicence;
    @Column(name = "pays")
    private String pays;
    @Column(name = "isPro")
    private Boolean isPro;
    @Column(name = "isFree")
    private Boolean isFree;
    @ManyToOne
    @JoinColumn(name = "partenaireId")
    private Inspection partenaire;
    @OneToMany(mappedBy = "licence")
    private List<DemandeLicence> demandeLicences;

}
