package org.ghizlane.gestion_bibliotheque_pfe.repositories;


import org.ghizlane.gestion_bibliotheque_pfe.models.Categorie;
import org.ghizlane.gestion_bibliotheque_pfe.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepositories extends JpaRepository<Promotion, Integer> {

    @Query("SELECT c FROM Promotion c WHERE " +
            "CAST(c.id_promotion AS string) LIKE %:search% OR " +
            "LOWER(c.titre) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Promotion> findByAny(@Param("search") String search);

}
