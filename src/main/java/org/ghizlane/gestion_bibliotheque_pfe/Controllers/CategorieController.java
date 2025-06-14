package org.ghizlane.gestion_bibliotheque_pfe.Controllers;



import org.ghizlane.gestion_bibliotheque_pfe.Services.CategorieService;
import org.ghizlane.gestion_bibliotheque_pfe.models.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class CategorieController {


    @Autowired
    private CategorieService categorieService;

    @GetMapping("/categories")
    public String goCategories(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Categorie> categorieList;

        if (search != null && !search.isEmpty()) {
            categorieList = categorieService.searchCategories(search);
        } else {
            categorieList = categorieService.getCategories();
        }

        categorieList.sort(Comparator.comparing(Categorie::getIdCategorie));
        model.addAttribute("categories", categorieList);
        model.addAttribute("search", search); // Pour garder la valeur saisie
        return "categorie";
    }


    @PostMapping("/categories/addNew")
    public String addNew(Categorie categorie) {

        categorieService.save(categorie);

        return "redirect:/categories";
    }

    @RequestMapping("/categories/findById/")
    @ResponseBody
    public Optional<Categorie> findById(int idCategorie) {
        return categorieService.findById(idCategorie);

    }


    @RequestMapping(value="/categories/update", method = {RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST})
    public String update(Categorie categorie) {

        categorieService.save(categorie);

        return "redirect:/categories";
    }
    @RequestMapping(value="/categories/delete/", method = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST})
    public String delete(Integer idCategorie) {

        categorieService.delete(idCategorie);

        return "redirect:/categories";
    }


}
