
package org.ghizlane.gestion_bibliotheque_pfe.Services;

import org.ghizlane.gestion_bibliotheque_pfe.models.Categorie;
import org.ghizlane.gestion_bibliotheque_pfe.models.Utilisateur;
import org.ghizlane.gestion_bibliotheque_pfe.repositories.UtilisateurRepsitories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepsitories utilisateurReps;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(Utilisateur utilisateur) {
        if (utilisateur.getRole() == null) {
            utilisateur.setRole("ROLE_USER");
        }
        utilisateur.setActif(true);
        utilisateur.setPassword(bCryptPasswordEncoder.encode(utilisateur.getPassword()));
        utilisateurReps.save(utilisateur);
    }

    public void updateUtilisateur(Utilisateur utilisateur) {
        // Récupère l'utilisateur actuel depuis la base
        Utilisateur utilisateurExistant = utilisateurReps.findById(utilisateur.getIdUtilisateur()).orElse(null);

        if (utilisateurExistant != null) {
            // Conserver le mot de passe original
            utilisateur.setPassword(utilisateurExistant.getPassword());

            // Conserver l'image si elle n'est pas changée (facultatif)
            if (utilisateur.getPhoto() == null || utilisateur.getPhoto().isEmpty()) {
                utilisateur.setPhoto(utilisateurExistant.getPhoto());
            }


            // Sauvegarder les nouvelles infos
            utilisateurReps.save(utilisateur);
        }
    }


    public Utilisateur findByEmail(String email) {
        return utilisateurReps.findByEmail(email);
    }

    //  récupérer l'utilisateur connecté
    public Utilisateur getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername(); //  l’email
        } else {
            email = principal.toString();
        }

        return findByEmail(email);
    }
}