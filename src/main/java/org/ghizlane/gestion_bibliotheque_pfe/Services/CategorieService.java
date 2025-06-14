package org.ghizlane.gestion_bibliotheque_pfe.Services;

import jakarta.transaction.Transactional;
import org.ghizlane.gestion_bibliotheque_pfe.models.Categorie;
import org.ghizlane.gestion_bibliotheque_pfe.repositories.CategorieRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepositories categorieRepositories;

    //Return list of countries
    @Transactional
    public List<Categorie> getCategories() {

        return  categorieRepositories.findAll();
    }


    //Save New Categorie
    public void save(Categorie categorie) {
        categorieRepositories.save(categorie);
    }

    //get by id
    public Optional<Categorie> findById(int idCategorie) {
        return categorieRepositories.findById(idCategorie);

    }


    public void delete(Integer idCategorie) {
        categorieRepositories.deleteById(idCategorie);

    }


    public List<Categorie> searchCategories(String search) {
        return categorieRepositories.findByAny(search);
    }
}
