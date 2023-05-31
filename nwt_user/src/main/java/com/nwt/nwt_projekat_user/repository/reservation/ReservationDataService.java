package com.nwt.nwt_projekat_user.repository.reservation;

import com.nwt.nwt_projekat_user.models.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationDataService {

    @Autowired
    ReservationRepository reservationRepository;

    public Reservation getReservationById(Long id){
        return reservationRepository.getById(id);
    }

    public void createOrUpdateReservation(Reservation customUser){
        reservationRepository.save(customUser);
    }

}

