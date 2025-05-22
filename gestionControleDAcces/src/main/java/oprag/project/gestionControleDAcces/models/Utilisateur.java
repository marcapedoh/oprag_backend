package oprag.project.gestionControleDAcces.models;

import jakarta.persistence.*;
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
public class Utilisateur extends AbstractEntity implements UserDetails {
    @Column(name = "nom", nullable = false)
    private String nom;
    @Column(name = "prenom", nullable = false)
    private String prenom;
    @Column(name = "userName", nullable = false,unique = true)
    private String userName;
    @Column(name = "email", nullable = false,unique = true)
    private String email;
    @Column(name = "motDePasse",nullable = false)
    private String motDePasse;
    @Column(name = "premiereConnexion",nullable = false)
    private boolean premiereConnexion;
    @Column(name = "otpNumber",nullable = false)
    private Integer otpNumber;
    @Column(name = "active")
    private boolean active;
    @Column(name = "signature")
    private String signature;
    @Column(name = "userRole", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "InspectionId")
    private Inspection inspection;

    @OneToMany(mappedBy = "inspecteur")
    private List<Badge> badges;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
