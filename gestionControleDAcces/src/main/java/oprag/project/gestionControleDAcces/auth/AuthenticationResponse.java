package oprag.project.gestionControleDAcces.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oprag.project.gestionControleDAcces.models.UserRole;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private int userId;
    private boolean active;
    private UserRole role;
    private Integer otpNumber;
    private String inspectionName;
}
