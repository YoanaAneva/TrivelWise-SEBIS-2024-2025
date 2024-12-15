package com.example.travelwise.service;

import com.example.travelwise.dto.ReservationDTO;
import com.example.travelwise.dto.TravelerDetailsDTO;
import com.example.travelwise.entity.Reservation;
import com.example.travelwise.entity.TravelerDetails;
import com.example.travelwise.mapper.ReservationMapper;
import com.example.travelwise.mapper.TravelerDetailsMapper;
import com.example.travelwise.repository.ReservationRepository;
import com.example.travelwise.repository.TravelerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final TravelerDetailsRepository travelerDetailsRepository;
    private final TravelerDetailsMapper travelerDetailsMapper;
    private final CartService cartService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              ReservationMapper reservationMapper,
                              TravelerDetailsRepository travelerDetailsRepository,
                              TravelerDetailsMapper travelerDetailsMapper,
                              CartService cartService) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.travelerDetailsRepository = travelerDetailsRepository;
        this.travelerDetailsMapper = travelerDetailsMapper;
        this.cartService = cartService;
    }

    public List<ReservationDTO> getReservationsByCart(Long cartId, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return reservationRepository.findByCartId(cartId, pageable)
                .stream()
                .map(reservationMapper::mapToDTO)
                .toList();
    }

    public List<ReservationDTO> getReservationsByUser(Long userId, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return reservationRepository.findByCartUserId(userId, pageable)
                .stream()
                .map(reservationMapper::mapToDTO)
                .toList();
    }

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation newReservation = reservationMapper.mapToEntity(reservationDTO);
        reservationRepository.save(newReservation);
        for (TravelerDetailsDTO detailsDTO : reservationDTO.getTravelers()) {
            TravelerDetails newDetails = travelerDetailsMapper.mapToEntity(detailsDTO);
            travelerDetailsRepository.save(newDetails);
            System.out.println("New Details:\n" + newDetails);
            newReservation.getTravelers().add(newDetails);
        }
        cartService.addReservationToCart(reservationDTO.getCartId(), reservationDTO.getTotalPrice());
        return reservationMapper.mapToDTO(newReservation);
    }

    public void deleteReservation(Long reservationId) {
        Reservation toBeDeleted = reservationRepository.findById(reservationId).orElse(null);
        if(toBeDeleted != null) {
            cartService.removeReservationFromCart(toBeDeleted.getCart().getId(), toBeDeleted.getTotalPrice());
        }
        reservationRepository.deleteById(reservationId);
    }

    public void payReservationsInCart(Long cartId) {
        List<Reservation> reservationsInCart = reservationRepository.findByCartId(cartId);
        for (Reservation reservation : reservationsInCart) {
            reservation.setPaid(true);
        }
        reservationRepository.saveAll(reservationsInCart);
    }
}
