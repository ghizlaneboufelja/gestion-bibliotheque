package org.ghizlane.gestion_bibliotheque_pfe.Controllers;

import org.ghizlane.gestion_bibliotheque_pfe.Services.UtilisateurService;
import org.ghizlane.gestion_bibliotheque_pfe.models.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping(value="/utilisateurs/addNew")
    public RedirectView addNew(Utilisateur utilisateur,
                               @RequestParam("photoFile") MultipartFile photoFile,
                               RedirectAttributes redir) throws IOException {

        // Enregistre le fichier si présent
        if (!photoFile.isEmpty()) {
            String fileName = utilisateur.getPrenom() + ".jpg";  // ou autre logique de nommage
            Path uploadPath = Paths.get("src/main/resources/static/img/photos");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = photoFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                utilisateur.setPhoto(fileName); // On enregistre juste le nom dans la base
            }
        }

        utilisateurService.save(utilisateur);
        RedirectView redirectView = new RedirectView("/login", true);
        redir.addFlashAttribute("message", "Inscription réussie  >^_^<  ! Vous pouvez maintenant vous connecter.");
        return redirectView;
    }
}

