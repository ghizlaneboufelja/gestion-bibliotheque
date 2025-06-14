package org.ghizlane.gestion_bibliotheque_pfe.Controllers;


import org.ghizlane.gestion_bibliotheque_pfe.Services.EmpruntService;
import org.ghizlane.gestion_bibliotheque_pfe.Services.LivreService;
import org.ghizlane.gestion_bibliotheque_pfe.Services.RetourService;
import org.ghizlane.gestion_bibliotheque_pfe.Services.UtilisateurService;
import org.ghizlane.gestion_bibliotheque_pfe.models.Adherent;
import org.ghizlane.gestion_bibliotheque_pfe.models.Emprunt;
import org.ghizlane.gestion_bibliotheque_pfe.models.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/emprunts")
public class EmpruntController {

    @Autowired
    private EmpruntService empruntService;

    @Autowired
    private RetourService retourService;

    @Autowired
    private LivreService livreService;

    @Autowired
    private UtilisateurService utilisateurService;

    // Emprunter un livre
    @PostMapping("/{id}/emprunter")
    public String emprunterLivre(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Livre livre = livreService.getLivreById(id);
        Adherent adherent = (Adherent) utilisateurService.getCurrentUser();

        try {
            empruntService.emprunterLivre(adherent, livre);
            redirectAttributes.addFlashAttribute("success", "Livre emprunté avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Impossible d'emprunter : " + e.getMessage());
        }
        return "redirect:/livres/" + id;
    }

    // Retourner un livre
    @PostMapping("/{id}/retourner")
    public String retournerLivre(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Adherent adherent = (Adherent) utilisateurService.getCurrentUser();

        Optional<Emprunt> empruntOpt = empruntService.getEmpruntByAdherentAndLivre(adherent.getIdUtilisateur(), id);

        if (empruntOpt.isPresent()) {
            retourService.retournerLivre(empruntOpt.get());
            redirectAttributes.addFlashAttribute("success", "Livre retourné avec succès !");
        } else {
            redirectAttributes.addFlashAttribute("error", "Aucun emprunt trouvé.");
        }
        return "redirect:/livres/" + id;
    }

    // Afficher la liste des emprunts pour l'adhérent connecté
    @GetMapping("/mes-emprunts")
    public String mesEmprunts(Model model) {
        Adherent adherent = (Adherent) utilisateurService.getCurrentUser();
        if (adherent == null) {
            return "redirect:/login";
        }
        List<Emprunt> emprunts = empruntService.getEmpruntsByAdherent(adherent.getIdUtilisateur());
        model.addAttribute("emprunts", emprunts);
        return "mes-emprunts"; // une page Thymeleaf à créer pour afficher la liste
    }
}
