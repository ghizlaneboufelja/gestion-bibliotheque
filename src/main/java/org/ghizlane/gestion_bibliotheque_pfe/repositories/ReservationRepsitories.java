package org.ghizlane.gestion_bibliotheque_pfe.repositories;

import org.ghizlane.gestion_bibliotheque_pfe.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepsitories extends JpaRepository<Reservation, Integer> {

    // Trouver les réservations d’un adhérent
    List<Reservation> findByAdherent_IdUtilisateur(int id);

    // Vérifier si un adhérent a déjà réservé un livre
    Optional<Reservation> findByAdherent_IdUtilisateurAndLivre_IdLivre(int adherentId, int livreId);
}
