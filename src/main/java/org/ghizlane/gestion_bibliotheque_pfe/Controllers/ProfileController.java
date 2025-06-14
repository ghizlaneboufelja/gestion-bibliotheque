package org.ghizlane.gestion_bibliotheque_pfe.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.ghizlane.gestion_bibliotheque_pfe.Services.UtilisateurService;
import org.ghizlane.gestion_bibliotheque_pfe.models.UserPrincipal;
import org.ghizlane.gestion_bibliotheque_pfe.models.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.UUID;


@Controller
public class ProfileController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/profile")
    public String profile(Model model, Principal principal,
                          @RequestParam(value = "success", required = false) String success,
                          @RequestParam(value = "photoUpdated", required = false) String photoUpdated,
                          @RequestParam(value = "error", required = false) String error) {

        // Récupérer l'utilisateur complet depuis la base de données
        Utilisateur utilisateur = utilisateurService.findByEmail(principal.getName());
        model.addAttribute("utilisateur", utilisateur);

        if (success != null) model.addAttribute("message", "Profil mis à jour avec succès !");
        if (photoUpdated != null) model.addAttribute("message", "Photo de profil mise à jour !");
        if (error != null) model.addAttribute("errorMessage", "Une erreur est survenue, veuillez réessayer.");

        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String nom,
                                @RequestParam String prenom,
                                @RequestParam String email,
                                Principal principal,
                                HttpServletRequest request,
                                HttpSession session) {

        try {
            Utilisateur utilisateur = utilisateurService.findByEmail(principal.getName());

            // Mise à jour des champs
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);

            // Gestion spéciale pour l'email
            if (!utilisateur.getEmail().equals(email)) {
                utilisateur.setEmail(email);
            }

            utilisateurService.updateUtilisateur(utilisateur);

            // Mise à jour du contexte de sécurité
            UserPrincipal userPrincipal = new UserPrincipal(utilisateur);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userPrincipal, utilisateur.getPassword(), userPrincipal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);



            return "redirect:/profile?success";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/profile?error";
        }
    }
    @PostMapping("/profile/photo/update")
    public String updatePhoto(@RequestParam("photo") MultipartFile file,
                              RedirectAttributes redirectAttributes,
                              HttpServletRequest request) {
        Utilisateur utilisateur = utilisateurService.getCurrentUser();

        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                String newFilename = UUID.randomUUID() + "_" + originalFilename;
                String uploadDir = new ClassPathResource("static/img/photos/").getFile().getAbsolutePath();
                Path uploadPath = Paths.get(uploadDir);
                Files.createDirectories(uploadPath);

                Path filePath = uploadPath.resolve(newFilename);
                file.transferTo(filePath.toFile());

                utilisateur.setPhoto(newFilename);
                utilisateurService.save(utilisateur);

                // Mettre à jour le contexte de sécurité
                UserPrincipal userPrincipal = new UserPrincipal(utilisateur);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userPrincipal, utilisateur.getPassword(), userPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Ajouter un paramètre de cache busting
                redirectAttributes.addAttribute("photoUpdated", System.currentTimeMillis());
                redirectAttributes.addFlashAttribute("message", "Photo de profil mise à jour avec succès !");
                return "redirect:/profile";

            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors du téléchargement de la photo.");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Veuillez sélectionner un fichier.");
        }

        return "redirect:/profile";
    }


}
