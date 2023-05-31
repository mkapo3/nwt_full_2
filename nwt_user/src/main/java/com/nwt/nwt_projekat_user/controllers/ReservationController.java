package com.nwt.nwt_projekat_user.controllers;

import com.nwt.nwt_projekat_user.clients.ReservationClient;
import com.nwt.nwt_projekat_user.error.exception.WrappedException;
import com.nwt.nwt_projekat_user.models.CustomUser;
import com.nwt.nwt_projekat_user.models.Reservation;
import com.nwt.nwt_projekat_user.repository.reservation.ReservationDataService;
import com.nwt.nwt_projekat_user.repository.user.CustomUserDataService;
import com.nwt.nwt_projekat_user.request_response.GeneralSuccessResponse;
import com.nwt.nwt_projekat_user.request_response.reservation.ReservationReq;
import com.nwt.nwt_projekat_user.services.CurrentUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.nwt.nwt_projekat_user.error.ErrorConstants.NOT_FOUND;

@RestController
@RequestMapping("/user/reservation")
public class ReservationController {

    @Autowired
    ReservationDataService reservationDataService;

    @Autowired
    CurrentUserService currentUserService;

    @Autowired
    CustomUserDataService customUserDataService;

    @Autowired
    ReservationClient reservationClient;

    @PostMapping("")
    @ResponseBody
    public GeneralSuccessResponse createReservation(@RequestBody ReservationReq reservationReq){

        CustomUser customUser = customUserDataService.getUserByEmail(currentUserService.getEmail());
        if (customUser == null){
            customUser = customUserDataService.getUserById(reservationReq.getUserId());
        }
        Reservation reservation = new Reservation();
        reservation.setProductId(reservationReq.getProductId());
        reservation.setCustomUser(customUser);
        customUser.getReservations().add(reservation);

        customUserDataService.createOrUpdateUser(customUser);

        LocalDateTime localDateTime = LocalDateTime.now();
        reservationClient.addReservation(customUser.getId(), localDateTime, reservation.getProductId());

        return new GeneralSuccessResponse();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Reservation getReservation(@PathVariable Long id, HttpServletRequest request){
        Reservation reservation = reservationDataService.getReservationById(id);
        if (reservation == null){
            throw new WrappedException(NOT_FOUND);
        }
        return reservation;
    }


}
