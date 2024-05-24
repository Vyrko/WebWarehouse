package com.example.WebWarehouse.entity;

import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email",unique = true)
    @NotEmpty(message = "Поле не должно быть пустым!")
    @Email(message = "Адрес почты должен быть валидным")
    private String email;

    @NotEmpty(message = "Поле не должно быть пустым!")
    @Size(min = 2, max = 30, message = "Имя должно состоять от 2 до 30 символов")
    private String name;
    @Pattern(regexp = "\\+375(17|25|29|33|44)\\d{7}", message = "Не верный формат")
    private String phoneNumber;

    @Column(name = "active")
    private boolean active;
    @Column(name = "password", length = 1000)
    private String password;
    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> role=new HashSet<>();
    private Date dataOfCreated;
    private boolean busy;
    @Column(name = "status")
    private String status;

    @PrePersist
    private void init(){dataOfCreated=new Date();}

    public boolean isAdmin(){return role.contains(Role.ROLE_ADMIN);}
    public boolean isWorker(){return role.contains(Role.ROLE_USER);}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
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
        return active;
    }
}

