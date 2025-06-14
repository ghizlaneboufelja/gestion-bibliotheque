package org.ghizlane.gestion_bibliotheque_pfe.Services;

import org.ghizlane.gestion_bibliotheque_pfe.models.Emprunt;
import org.ghizlane.gestion_bibliotheque_pfe.repositories.EmpruntRepsitories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class RetourService {

    @Autowired
    private EmpruntRepsitories empruntRepsitories;

    @Autowired
    private LivreService livreService;

    public void retournerLivre(Emprunt emprunt) {
        if (emprunt.getDate_retour() != null) {
            throw new IllegalStateException("Livre déjà retourné !");
        }

        emprunt.setDate_retour(new Date());
        empruntRepsitories.save(emprunt);
        livreService.updateNbrExemplaires(emprunt.getLivre(), 1);
    }
}
