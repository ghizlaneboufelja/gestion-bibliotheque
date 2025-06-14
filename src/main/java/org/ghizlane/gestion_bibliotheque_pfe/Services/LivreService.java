package org.ghizlane.gestion_bibliotheque_pfe.Services;


import org.ghizlane.gestion_bibliotheque_pfe.models.Categorie;
import org.ghizlane.gestion_bibliotheque_pfe.models.Livre;
import org.ghizlane.gestion_bibliotheque_pfe.repositories.LivreRepsitories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LivreService {

    @Autowired
    private LivreRepsitories livreRepsitories;

    @Autowired
    private CategorieService categorieService;

    // Return list of livres
    public List<Livre> getLivres() {
        return livreRepsitories.findAll();
    }

    // Return list of livres trié
    public List<Livre> getAllLivresSorted() {
        return livreRepsitories.findAll(Sort.by(Sort.Direction.ASC, "idLivre"));
    }



    public List<Livre> findAllById(List<Integer> ids) {
        return livreRepsitories.findAllById(ids);
    }

    // Save new livre
    public void save(Livre livre) {
        // Si la catégorie a un ID, on la charge depuis la base
        if (livre.getCategorie() != null && livre.getCategorie().getIdCategorie() != 0) {
            Categorie existingCategorie = categorieService.findById(livre.getCategorie().getIdCategorie())
                    .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
            livre.setCategorie(existingCategorie);
        }
        livreRepsitories.save(livre);
    }

    // get by id
    public Optional<Livre> findById(int idLivre) {
        Optional<Livre> optLivre = livreRepsitories.findById(idLivre);

        optLivre.ifPresent(livre -> {
            // Force le chargement de la catégorie
            if (livre.getCategorie() != null) {
                livre.getCategorie().getNom(); // Accès déclenche le chargement
            }
        });

        return optLivre;
    }

    public Livre getLivreById(int idLivre) {
        return livreRepsitories.findById(idLivre)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé avec l'ID : " + idLivre));
    }


    // Supprimer livre
    public void delete(Integer idLivre) {
        livreRepsitories.deleteById(idLivre);
    }

    public List<Livre> searchLivres(String search) {
        return livreRepsitories.findByAny(search);
    }

    public void updateNbrExemplaires(Livre livre, int nb) {
        livre.setNbrExempDisponibles(livre.getNbrExempDisponibles() + nb);
        livreRepsitories.save(livre);
    }

    public boolean isDisponible(Livre livre) {
        return livre.getNbrExempDisponibles() > 0;
    }


}