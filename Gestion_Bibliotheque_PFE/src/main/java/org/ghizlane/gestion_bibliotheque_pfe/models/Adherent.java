package org.ghizlane.gestion_bibliotheque_pfe.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "adherent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Adherent extends Utilisateur{

    // Un adhérent peut faire plusieurs emprunts
    @OneToMany(mappedBy = "adherent", cascade = CascadeType.ALL)
    private List<Emprunt> emprunts;

    // Un adhérent peut faire plusieurs réservations
    @OneToMany(mappedBy = "adherent", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
}
