package org.ghizlane.gestion_bibliotheque_pfe.repositories;

import org.ghizlane.gestion_bibliotheque_pfe.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UtilisateurRepsitories extends JpaRepository<Utilisateur, Integer> {

    Utilisateur findByEmail(@Param("email") String email);
}
