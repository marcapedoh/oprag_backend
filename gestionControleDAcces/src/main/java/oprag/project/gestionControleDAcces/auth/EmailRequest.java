package oprag.project.gestionControleDAcces.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class EmailRequest {
    private String to;
    private String subject;
    private String text;
}