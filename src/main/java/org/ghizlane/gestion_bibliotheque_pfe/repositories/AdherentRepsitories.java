package org.ghizlane.gestion_bibliotheque_pfe.repositories;

import org.ghizlane.gestion_bibliotheque_pfe.models.Adherent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdherentRepsitories extends JpaRepository<Adherent, Integer> {

    // Chercher un adhérent par son email (utile pour l’authentification)
    Optional<Adherent> findByEmail(String email);
}
