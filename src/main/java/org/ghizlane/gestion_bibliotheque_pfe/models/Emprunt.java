package org.ghizlane.gestion_bibliotheque_pfe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "emprunt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmprunt;

    @Temporal(TemporalType.DATE)
    private Date date_emprunt;

    @Temporal(TemporalType.DATE)
    private Date date_retour;

    @ManyToOne
    @JoinColumn(name = "adherent_id")
    private Adherent adherent;

    @ManyToOne
    @JoinColumn(name = "livre_id")
    private Livre livre;



}
