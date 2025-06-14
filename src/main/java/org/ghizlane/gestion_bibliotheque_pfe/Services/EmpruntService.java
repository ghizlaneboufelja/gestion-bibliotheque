package org.ghizlane.gestion_bibliotheque_pfe.Services;

import org.ghizlane.gestion_bibliotheque_pfe.models.Adherent;
import org.ghizlane.gestion_bibliotheque_pfe.models.Emprunt;
import org.ghizlane.gestion_bibliotheque_pfe.models.Livre;
import org.ghizlane.gestion_bibliotheque_pfe.repositories.EmpruntRepsitories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmpruntService {

    @Autowired
    private EmpruntRepsitories empruntRepsitories;

    @Autowired
    private LivreService livreService;

    public Emprunt emprunterLivre(Adherent adherent, Livre livre) {
        if (!livreService.isDisponible(livre)) {
            throw new IllegalStateException("Livre indisponible");
        }

        Emprunt emprunt = new Emprunt();
        emprunt.setAdherent(adherent);
        emprunt.setLivre(livre);
        emprunt.setDate_emprunt(new Date());
        emprunt.setDate_retour(null); // on fixera la date de retour plus tard

        livreService.updateNbrExemplaires(livre, -1);
        return empruntRepsitories.save(emprunt);
    }

    public List<Emprunt> getEmpruntsByAdherent(int idAdherent) {
        return empruntRepsitories.findByAdherent_IdUtilisateur(idAdherent);
    }

    public Optional<Emprunt> getEmpruntByAdherentAndLivre(int idAdherent, int idLivre) {
        return empruntRepsitories.findByAdherent_IdUtilisateurAndLivre_IdLivre(idAdherent, idLivre);
    }

}
