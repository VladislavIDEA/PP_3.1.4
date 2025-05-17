package ilyasov.example.pprestcontrollers.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Имя не может быть пустым")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Фамилия не может быть пустым")
    @Column(name = "lastname")
    private String lastname;


    @NotNull
    @Min(1)
    @Max(120)
    @Column(name = "age")
    private Byte age;

    @Email
    @Column(name = "email",unique = true)
    @NotEmpty(message = "Email не может быть пустым")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
