package oprag.project.gestionControleDAcces.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
public class Chauffeur extends AbstractEntity implements UserDetails {
    @Column(name = "numeroPermis",nullable = false)
    private String numeroPermis;
    @Column(name = "nom",unique = true,nullable = false)
    private String nom;
    @Column(name = "prenom",nullable = false)
    private String prenom;
    @Column(name = "numeroCertificatMedicalValide",nullable = false)
    private String numeroCertificatMedicalValide;
    @Column(name = "numeroVisiteTechnique",nullable = false)
    private String numeroVisiteTechnique;
    @Column(name = "attestionCapPoidsLourd",nullable = false)
    private String attestionCapPoidsLourd;
    @Column(name = "attestationDeConduiteDefensive",nullable = false)
    private String attestationDeConduiteDefensive;
    @Column(name = "userName")
    private String userName;
    @Column(name = "isActive")
    private boolean active;
    @Column(name = "motDePasse")
    private String motDePasse;

    @OneToMany(mappedBy = "chauffeur")
    private List<CertificatControl> certificatControl;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("CHAUFFEUR"));
    }

    @Override
    public String getPassword() {
        return this.motDePasse;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
