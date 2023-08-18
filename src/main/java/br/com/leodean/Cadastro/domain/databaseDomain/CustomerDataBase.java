package br.com.leodean.Cadastro.domain.databaseDomain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "TB_Customer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDataBase implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Cutomer_id", nullable = false)
    private String customerID;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Cell", nullable = false)
    private String cell;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Senha", nullable = false)
    private String password;

    @Column(name = "CPF", nullable = false)
    private String CPF;

    @Column(name = "Data_Criação", nullable = false)
    private LocalDateTime localDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
