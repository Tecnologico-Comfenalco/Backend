package com.tecno_comfenalco.pa.security.domain;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")

@Data
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    private boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Devuelve true si la cuenta no ha expirado
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Devuelve true si la cuenta no está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Devuelve true si las credenciales no han expirado
    }

    @Override
    public boolean isEnabled() {
        return this.enabled; // Devuelve true si la cuenta está habilitada
    }

}
