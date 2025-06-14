package org.ghizlane.gestion_bibliotheque_pfe.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLivre;

    private String titre;
    private String auteur;
    private int annee_edition;

    private int nbrExempDisponibles;

    @ManyToOne
    @JoinColumn(name = "categorie_id", referencedColumnName = "idCategorie")
    @JsonIgnoreProperties("livres")
    private Categorie categorie;


    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL)
    private List<Emprunt> emprunts;

    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @ManyToMany(mappedBy = "livres")
    @JsonIgnoreProperties("livres")
    private List<Promotion> promotions = new ArrayList<>();


}
