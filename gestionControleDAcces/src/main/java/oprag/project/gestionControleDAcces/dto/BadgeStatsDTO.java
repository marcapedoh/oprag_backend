package oprag.project.gestionControleDAcces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BadgeStatsDTO {
    private LocalDate date;
    private long count;
}
