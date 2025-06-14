package org.ghizlane.gestion_bibliotheque_pfe.Controllers;

import org.ghizlane.gestion_bibliotheque_pfe.Services.LivreService;
import org.ghizlane.gestion_bibliotheque_pfe.Services.ReservationService;
import org.ghizlane.gestion_bibliotheque_pfe.Services.UtilisateurService;
import org.ghizlane.gestion_bibliotheque_pfe.models.Adherent;
import org.ghizlane.gestion_bibliotheque_pfe.models.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private LivreService livreService;

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/{id}/reserver")
    public String reserverLivre(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Livre livre = livreService.getLivreById(id);
        Adherent adherent = (Adherent) utilisateurService.getCurrentUser();

        reservationService.reserverLivre(adherent, livre);
        redirectAttributes.addFlashAttribute("success", "Livre réservé avec succès !");
        return "redirect:/livres/" + id;
    }
}
