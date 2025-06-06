package org.ghizlane.gestion_bibliotheque_pfe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "rapport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rapport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRapport;

    @Temporal(TemporalType.DATE)
    private Date dateGeneration;

    @Lob
    private String contenu;

    @ManyToOne
    @JoinColumn(name = "administrateur_id")
    private Administrateur administrateur;

    // Rapport concerne plusieurs livres
    @ManyToMany
    private List<Livre> livres;

    // Méthode métier
    public void genererRapport() {}
}
