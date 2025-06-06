package org.ghizlane.gestion_bibliotheque_pfe.Services;


import org.ghizlane.gestion_bibliotheque_pfe.models.Categorie;
import org.ghizlane.gestion_bibliotheque_pfe.models.Livre;
import org.ghizlane.gestion_bibliotheque_pfe.repositories.LivreRepsitories;
import org.springframework.beans.factory.annotation.Autowired;
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


    public List<Livre> findAllById(List<Integer> ids) {
        return livreRepsitories.findAllById(ids);
    }

    // Save new livre
    public void save(Livre livre) {
        // Si la catégorie a un ID, on la charge depuis la base
        if(livre.getCategorie() != null && livre.getCategorie().getId_categorie() != 0) {
            Categorie existingCategorie = categorieService.findById(livre.getCategorie().getId_categorie())
                    .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
            livre.setCategorie(existingCategorie);
        }
        livreRepsitories.save(livre);
    }

    // get by id
    public Optional<Livre> findById(int id_livre) {
        Optional<Livre> optLivre = livreRepsitories.findById(id_livre);

        optLivre.ifPresent(livre -> {
            // Force le chargement de la catégorie
            if (livre.getCategorie() != null) {
                livre.getCategorie().getNom(); // Accès déclenche le chargement
            }
        });

        return optLivre;
    }

    // Supprimer livre
    public void delete(Integer id_livre) {
        livreRepsitories.deleteById(id_livre);
    }

    public List<Livre> searchLivres(String search) {
        return livreRepsitories.findByAny(search);
    }
}