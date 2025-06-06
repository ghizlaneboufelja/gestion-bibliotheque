package org.ghizlane.gestion_bibliotheque_pfe.Services;

import jakarta.transaction.Transactional;
import org.ghizlane.gestion_bibliotheque_pfe.models.Livre;
import org.ghizlane.gestion_bibliotheque_pfe.models.Promotion;
import org.ghizlane.gestion_bibliotheque_pfe.repositories.LivreRepsitories;
import org.ghizlane.gestion_bibliotheque_pfe.repositories.PromotionRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepositories promotionRepositories;
    @Autowired
    private LivreRepsitories livreRepsitories;

    //Return list of countries
    @Transactional
    public List<Promotion> getPromotions() {

        return  promotionRepositories.findAll();
    }

    public List<Livre> findAllById(List<Integer> ids) {
        return livreRepsitories.findAllById(ids);
    }

    //Save New Country
    public Promotion save(Promotion promotion) {
        // Étape 1 : sauvegarder la promotion sans les livres
        List<Livre> livres = promotion.getLivres();
        promotion.setLivres(null); // couper temporairement le lien
        Promotion savedPromotion = promotionRepositories.save(promotion);

        // Étape 2 : rattacher les livres après avoir obtenu l'ID
        savedPromotion.setLivres(livres);
        promotionRepositories.save(savedPromotion);
        return savedPromotion;
    }
    //get by id
    public Optional<Promotion> findById(int id_promotion) {
        return promotionRepositories.findById(id_promotion);

    }


    public void delete(Integer id_promotion) {
        promotionRepositories.deleteById(id_promotion);

    }


    public List<Promotion> searchCategories(String search) {
        return promotionRepositories.findByAny(search);
    }
}
