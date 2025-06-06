package org.ghizlane.gestion_bibliotheque_pfe.Controllers;


import org.ghizlane.gestion_bibliotheque_pfe.Services.CategorieService;
import org.ghizlane.gestion_bibliotheque_pfe.Services.LivreService;
import org.ghizlane.gestion_bibliotheque_pfe.models.Categorie;
import org.ghizlane.gestion_bibliotheque_pfe.models.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class LivreController {

    @Autowired
    private LivreService livreService;
    @Autowired
    private CategorieService categorieService;

    @GetMapping("/livres")
    public String goLivres(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Livre> livreList;

        if (search != null && !search.isEmpty()) {
            livreList = livreService.searchLivres(search);
        } else {
            livreList = livreService.getLivres();
        }

        livreList.sort(Comparator.comparing(Livre::getId_livre));
        model.addAttribute("livres", livreList);
        model.addAttribute("categories", categorieService.getCategories());
        model.addAttribute("search", search); // Pour garder la valeur saisie
        return "livre";
    }


    @PostMapping("/livres/addNew")
    public String  addNew(@ModelAttribute Livre livre)  {



        // Récupérer et attacher la catégorie si elle n'est pas déjà mappée (sécurité)
        Integer id_categorie = livre.getCategorie().getId_categorie();
        Categorie categorie = categorieService.findById(id_categorie).orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        livre.setCategorie(categorie);

        livreService.save(livre);
        return "redirect:/livres";
    }

    @RequestMapping("/livres/findById/")
    @ResponseBody
    public Optional<Livre> findById(int id_livre) {
        return livreService.findById(id_livre);

    }

    @RequestMapping(value="/livres/update", method = {RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST})
    public String update(Livre livre) {

        livreService.save(livre);

        return "redirect:/livres";
    }

    @RequestMapping(value="/livres/delete/", method = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST})
    public String delete(Integer id_livre) {

        livreService.delete(id_livre);

        return "redirect:/livres";
    }

}
