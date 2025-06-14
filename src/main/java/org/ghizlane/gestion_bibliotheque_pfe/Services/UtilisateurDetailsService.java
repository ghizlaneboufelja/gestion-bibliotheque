package org.ghizlane.gestion_bibliotheque_pfe.Services;


import org.ghizlane.gestion_bibliotheque_pfe.models.UserPrincipal;
import org.ghizlane.gestion_bibliotheque_pfe.models.Utilisateur;
import org.ghizlane.gestion_bibliotheque_pfe.repositories.UtilisateurRepsitories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UtilisateurDetailsService implements UserDetailsService {

    @Autowired UtilisateurRepsitories utilisateurRepsitories;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utilisateur user = utilisateurRepsitories.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found ! :( ");
        }
        return new UserPrincipal(user);
    }
}
