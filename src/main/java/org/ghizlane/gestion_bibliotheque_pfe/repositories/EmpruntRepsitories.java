package org.ghizlane.gestion_bibliotheque_pfe.repositories;

import org.ghizlane.gestion_bibliotheque_pfe.models.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmpruntRepsitories extends JpaRepository<Emprunt, Integer> {

    // Trouver les emprunts d’un adhérent
    List<Emprunt> findByAdherent_IdUtilisateur(int idAdherent);


    // Vérifier si un adhérent a déjà emprunté un livre spécifique
    Optional<Emprunt> findByAdherent_IdUtilisateurAndLivre_IdLivre(int idAdherent, int idLivre);

}
