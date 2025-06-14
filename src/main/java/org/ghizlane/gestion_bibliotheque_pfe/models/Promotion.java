package org.ghizlane.gestion_bibliotheque_pfe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "promotion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_promotion;

    private String titre;
    private String description;
    private String duree;
    private int nbrPromLimite;

    // Une promotion peut concerner plusieurs livres
    @ManyToMany
    private List<Livre> livres = new ArrayList<>();


}
