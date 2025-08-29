package oprag.project.gestionControleDAcces.models;

import jakarta.persistence.Entity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
public class InspectionMontant extends AbstractEntity{
    private Double montant;
}
