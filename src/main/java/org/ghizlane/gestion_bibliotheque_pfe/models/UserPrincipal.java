package org.ghizlane.gestion_bibliotheque_pfe.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private Utilisateur utilisateur;


    public UserPrincipal(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }



    public String getPrenom() {
        return utilisateur.getPrenom();
    }

    public String getNom() {
        return utilisateur.getNom();
    }

    public String getEmail() {
        return utilisateur.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Get role from user, ensure it's not null and starts with ROLE_
        String role = utilisateur.getRole() != null ?
                utilisateur.getRole() : "ROLE_USER";

        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return utilisateur.getPassword();
    }



    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
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
    public boolean isEnabled() {
        return true;
    }

    public String getPhoto() {
        return utilisateur.getPhoto();
    }

    public void updateUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

}
