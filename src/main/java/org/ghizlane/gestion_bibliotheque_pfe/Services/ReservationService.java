package org.ghizlane.gestion_bibliotheque_pfe.Services;

import org.ghizlane.gestion_bibliotheque_pfe.models.Adherent;
import org.ghizlane.gestion_bibliotheque_pfe.models.Livre;
import org.ghizlane.gestion_bibliotheque_pfe.models.Reservation;
import org.ghizlane.gestion_bibliotheque_pfe.repositories.ReservationRepsitories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepsitories reservationRepsitories;

    public Reservation reserverLivre(Adherent adherent, Livre livre) {
        Reservation reservation = new Reservation();
        reservation.setAdherent(adherent);
        reservation.setLivre(livre);
        reservation.setDate_reservation(new Date());
        return reservationRepsitories.save(reservation);
    }

    public List<Reservation> getReservationsByAdherent(int idAdherent) {
        return reservationRepsitories.findByAdherent_IdUtilisateur(idAdherent);
    }
}
