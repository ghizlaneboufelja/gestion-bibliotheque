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
@Table(name = "administrateur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Administrateur extends Utilisateur{

    @OneToMany(cascade = CascadeType.ALL)
    private List<Promotion> promotions;
}
