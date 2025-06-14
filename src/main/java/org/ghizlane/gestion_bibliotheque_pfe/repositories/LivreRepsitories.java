package org.ghizlane.gestion_bibliotheque_pfe.repositories;

import org.ghizlane.gestion_bibliotheque_pfe.models.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepsitories extends JpaRepository<Livre, Integer> {

    @Query("SELECT l FROM Livre l " +
            "WHERE CAST(l.idLivre AS string) LIKE %:search% " +
            "OR LOWER(l.titre) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(l.auteur) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR CAST(l.annee_edition AS string) LIKE %:search% " +
            "OR LOWER(l.categorie.nom) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Livre> findByAny(@Param("search") String search);

    List<Livre> findByCategorieIdCategorie(int idCategorie);


}
