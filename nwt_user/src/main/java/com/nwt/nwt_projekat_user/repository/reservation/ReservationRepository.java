package com.nwt.nwt_projekat_user.repository.reservation;

import com.nwt.nwt_projekat_user.models.CartProduct;
import com.nwt.nwt_projekat_user.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    public Reservation getById(Long id);

}
