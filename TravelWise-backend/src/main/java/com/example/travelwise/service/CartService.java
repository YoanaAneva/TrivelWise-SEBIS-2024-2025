package com.example.travelwise.service;

import com.amazonaws.services.acmpca.model.ResourceNotFoundException;
import com.example.travelwise.dto.CartDTO;
import com.example.travelwise.entity.Cart;
import com.example.travelwise.entity.Offer;
import com.example.travelwise.entity.Reservation;
import com.example.travelwise.mapper.CartMapper;
import com.example.travelwise.repository.CartRepository;
import com.example.travelwise.repository.OfferRepository;
import com.example.travelwise.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ReservationRepository reservationRepository;
    private final OfferRepository offerRepository;

    @Autowired
    public CartService(CartRepository cartRepository, CartMapper cartMapper,
                       ReservationRepository reservationRepository, OfferRepository offerRepository) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.reservationRepository = reservationRepository;
        this.offerRepository = offerRepository;
    }

    public List<CartDTO> getAllCarts() {
        return cartRepository.findAll().stream().map(cartMapper::mapToDTO).toList();
    }

    public Cart getCartById(Long id){
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No cart found with id " + id));
    }

    public CartDTO getCartDTOById(Long id){
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No cart found with id " + id));
        return cartMapper.mapToDTO(cart);
    }

    public CartDTO getCartByUser(Long userId){
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No cart found for user with id " + userId));
        return cartMapper.mapToDTO(cart);
    }

    public CartDTO createCart(CartDTO cartDTO) {
        Cart newCart = cartMapper.mapToEntity(cartDTO);
        cartRepository.save(newCart);
        return cartMapper.mapToDTO(newCart);
    }

    public CartDTO processCartPayment(Long cartId) {
        cartRepository.processCartPayment(cartId);
        return cartMapper.mapToDTO(cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("no cart with id: " + cartId)));
    }

//    public CartDTO payCart(Long cartId) {
//        Cart cart = cartRepository.findById(cartId)
//                .orElseThrow(() -> new ResourceNotFoundException("No cart found with id " + cartId));
//
//        payReservationsInCart(cartId);
//        cart.setNumberOfItems(0);
//        cart.setTotalPrice(0.0);
//
//        Cart emptyCart = cartRepository.save(cart);
//        return cartMapper.mapToDTO(emptyCart);
//    }

//    public void payReservationsInCart(Long cartId) {
//        List<Reservation> reservationsInCart = reservationRepository.findByCartIdAndIsPaidFalse(cartId);
//        for (Reservation reservation : reservationsInCart) {
//            reservation.setPaid(true);
//            updateAvailableSpotsInOffer(reservation.getOffer().getId(), reservation.getTravelers().size(), reservation.getOffer().getTitle());
//        }
//        reservationRepository.saveAll(reservationsInCart);
//    }

    public void addReservationToCart(Long cartId, Double newReservationPrice, Integer newItems) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new org.springframework.data.rest.webmvc.ResourceNotFoundException("No cart found with id: " + cartId));
        cart.setTotalPrice(cart.getTotalPrice() + newReservationPrice);
        cart.setNumberOfItems(cart.getNumberOfItems() + newItems);
        cartRepository.save(cart);
    }

    public void removeReservationFromCart(Long cartId, Double reservationPrice) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if(cart != null) {
            cart.setTotalPrice(cart.getTotalPrice() - reservationPrice);
            cart.setNumberOfItems(cart.getNumberOfItems() - 1);
            cartRepository.save(cart);
        }
    }

    public void deleteCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if(cart != null) {
            Long cartId = cart.getId();
            reservationRepository.deleteByCartId(cartId);
            cartRepository.deleteById(cartId);
        }
    }

//    private void updateAvailableSpotsInOffer(Long offerId, Integer takenSpots, String offerTitle) {
//        Offer offer = offerRepository.findById(offerId).orElse(null);
//        if(offer != null) {
//            Integer availableSpots = offer.getAvailableSpots();
//            if(takenSpots > availableSpots) {
//                throw new RuntimeException("Available spots less than requested for offer: " + offerTitle);
//            }
//            offer.setAvailableSpots(availableSpots - takenSpots);
//            offerRepository.save(offer);
//        }
//    }

    public Map<Long, Boolean> checkAvailability(Long cartId) {
        Map<Long, Boolean> availability = new HashMap<>();
        List<Reservation> reservations = reservationRepository.findByCartIdAndIsPaidFalse(cartId);
        for (Reservation reservation : reservations) {
            Offer offer = reservation.getOffer();
            if (offer.getAvailableSpots() >= reservation.getTravelers().size()) {
                availability.put(reservation.getId(), true);
            } else {
                availability.put(reservation.getId(), false);
            }
        }
        return availability;
    }
}
