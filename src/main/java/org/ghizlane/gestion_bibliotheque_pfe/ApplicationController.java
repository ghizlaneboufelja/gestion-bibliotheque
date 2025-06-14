package org.ghizlane.gestion_bibliotheque_pfe;


import org.ghizlane.gestion_bibliotheque_pfe.Services.CategorieService;
import org.ghizlane.gestion_bibliotheque_pfe.Services.LivreService;
import org.ghizlane.gestion_bibliotheque_pfe.Services.UtilisateurService;
import org.ghizlane.gestion_bibliotheque_pfe.models.Utilisateur;
import org.ghizlane.gestion_bibliotheque_pfe.repositories.UtilisateurRepsitories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class ApplicationController {

    @Autowired
    private   UtilisateurService utilisateurService;

    @Autowired
    private UtilisateurRepsitories utilisateurRepsitories;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private LivreService livreService;

    @GetMapping("/index")
    public String index(Model model, Principal principal) {

        if (principal != null) {
            Utilisateur user = utilisateurService.findByEmail(principal.getName());
            model.addAttribute("user", user);
        } else {
            // Pas d'utilisateur connecté
            model.addAttribute("user", null);
            // Ou tu peux rediriger vers une page de connexion, ou gérer autrement
            // return "redirect:/login";
        }

        model.addAttribute("livres", livreService.getAllLivresSorted());
        return "index";
    }



    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("messagErreur", "Email ou mot de passe incorrect.");
        }
        return "login";
    }


    @GetMapping("/logout")
    public String logout() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "register"; // Assurez-vous d'avoir register.html dans templates/
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Utilisateur utilisateur) {
        utilisateurService.save(utilisateur);
        return "redirect:/login"; // Redirection vers login après inscription
    }

    @GetMapping("/details")
    public String details(Model model) {

        return "details";
    }
}
