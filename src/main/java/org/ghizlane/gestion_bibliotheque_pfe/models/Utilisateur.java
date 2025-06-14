package org.ghizlane.gestion_bibliotheque_pfe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "utilisateur")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    @Id
    private int idUtilisateur;


    private String nom;
    private String prenom;
    private String email;
    private String password;

    @Column(name = "photo")
    private String photo;


    @Column(name = "role", nullable = false)
    private String role; // ROLE_ADMIN ou ROLE_USER

    private boolean actif = true; // pour activer/désactiver le compte

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
