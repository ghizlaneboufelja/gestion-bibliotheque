package org.ghizlane.gestion_bibliotheque_pfe.Controllers;

import org.ghizlane.gestion_bibliotheque_pfe.Services.CategorieService;
import org.ghizlane.gestion_bibliotheque_pfe.Services.LivreService;
import org.ghizlane.gestion_bibliotheque_pfe.Services.PromotionService;
import org.ghizlane.gestion_bibliotheque_pfe.models.Categorie;
import org.ghizlane.gestion_bibliotheque_pfe.models.Livre;
import org.ghizlane.gestion_bibliotheque_pfe.models.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private LivreService livreService;

    @GetMapping("/promotions")
    public String getPromotions(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Promotion> promotions = promotionService.getPromotions();
        List<Livre> livres = livreService.getLivres();

        model.addAttribute("promotions", promotions);
        model.addAttribute("livres", livres);
        model.addAttribute("search", search);

        return "promotion"; // Nom du fichier HTML ou JSP pour la page
    }

    @PostMapping("/promotions/addNew")
    public String addNew(@ModelAttribute Promotion promotion, @RequestParam("livreIds") List<Integer> livreIds) {

        // Étape 1 : sauvegarder la promotion sans livres pour générer l'ID
        Promotion savedPromotion = promotionService.save(promotion);

        // Étape 2 : récupérer les livres sélectionnés
        List<Livre> livres = livreService.findAllById(livreIds);

        // Étape 3 : associer les livres à la promotion
        savedPromotion.setLivres(livres);

        // Étape 4 : sauvegarder à nouveau la promotion avec les livres
        promotionService.save(savedPromotion);

        return "redirect:/promotions";
    }


    @RequestMapping("/promotions/findById/")
    @ResponseBody
    public Optional<Promotion> findById(int id_promotion) {
        return promotionService.findById(id_promotion);
    }

    @RequestMapping(value = "/promotions/update", method = {RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST})
    public String update(Promotion promotion, @RequestParam("livreIds") List<Integer> livreIds) {
        List<Livre> livres = livreService.findAllById(livreIds);
        promotion.setLivres(livres);
        promotionService.save(promotion);
        return "redirect:/promotions";
    }

    @RequestMapping(value = "/promotions/delete/", method = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST})
    public String delete(Integer id_promotion) {
        promotionService.delete(id_promotion);
        return "redirect:/promotions";
    }
}

