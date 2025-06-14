package org.ghizlane.gestion_bibliotheque_pfe.repositories;

import org.ghizlane.gestion_bibliotheque_pfe.models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepositories extends JpaRepository<Categorie, Integer> {

    @Query("SELECT c FROM Categorie c WHERE " +
            "CAST(c.idCategorie AS string) LIKE %:search% OR " +
            "LOWER(c.nom) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Categorie> findByAny(@Param("search") String search);

}
