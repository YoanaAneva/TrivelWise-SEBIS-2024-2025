package com.example.travelwise.service;

import com.example.travelwise.dto.ReservationDTO;
import com.example.travelwise.dto.TravelerDetailsDTO;
import com.example.travelwise.entity.Cart;
import com.example.travelwise.entity.Offer;
import com.example.travelwise.entity.Reservation;
import com.example.travelwise.entity.TravelerDetails;
import com.example.travelwise.mapper.ReservationMapper;
import com.example.travelwise.mapper.TravelerDetailsMapper;
import com.example.travelwise.repository.OfferRepository;
import com.example.travelwise.repository.ReservationRepository;
import com.example.travelwise.repository.TravelerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final TravelerDetailsRepository travelerDetailsRepository;
    private final TravelerDetailsMapper travelerDetailsMapper;
    private final CartService cartService;
    private final OfferRepository offerRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              ReservationMapper reservationMapper,
                              TravelerDetailsRepository travelerDetailsRepository,
                              TravelerDetailsMapper travelerDetailsMapper,
                              CartService cartService, OfferRepository offerRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.travelerDetailsRepository = travelerDetailsRepository;
        this.travelerDetailsMapper = travelerDetailsMapper;
        this.cartService = cartService;
        this.offerRepository = offerRepository;
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
        linkReservationToOffer(newReservation, reservationDTO.getOfferId());
        linkReservationToCart(newReservation, reservationDTO.getCartId());
        newReservation = reservationRepository.save(newReservation);

        for (TravelerDetailsDTO detailsDTO : reservationDTO.getTravelers()) {
            TravelerDetails newDetails = travelerDetailsMapper.mapToEntity(detailsDTO);
            newDetails.setReservation(newReservation);
            travelerDetailsRepository.save(newDetails);
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

    private void linkReservationToOffer(Reservation newReservation, Long offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException("No offer found with id: " + offerId));
        newReservation.setOffer(offer);
    }

    private void linkReservationToCart(Reservation newReservation, Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        newReservation.setCart(cart);
    }
}
