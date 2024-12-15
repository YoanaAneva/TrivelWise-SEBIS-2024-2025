package com.example.travelwise.controller;

import com.example.travelwise.dto.ReservationDTO;
import com.example.travelwise.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByCart(@PathVariable Long cartId,
                                                                         @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                         @RequestParam(required = false, defaultValue = "9") Integer limit) {
        return new ResponseEntity<>(reservationService.getReservationsByCart(cartId, page, limit), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUser(@PathVariable Long userId,
                                                                      @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                      @RequestParam(required = false, defaultValue = "9") Integer limit) {
        return new ResponseEntity<>(reservationService.getReservationsByUser(userId, page, limit), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        try{
            ReservationDTO newReservationDTO = reservationService.createReservation(reservationDTO);
            return new ResponseEntity<>(newReservationDTO, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
