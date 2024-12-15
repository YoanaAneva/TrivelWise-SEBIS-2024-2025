package com.example.travelwise.service;

import com.amazonaws.services.acmpca.model.ResourceNotFoundException;
import com.example.travelwise.dto.CartDTO;
import com.example.travelwise.entity.Cart;
import com.example.travelwise.mapper.CartMapper;
import com.example.travelwise.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ReservationService reservationService;

    @Autowired
    public CartService(CartRepository cartRepository, CartMapper cartMapper,
                       ReservationService reservationService) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.reservationService = reservationService;
    }

    public List<CartDTO> getAllCarts() {
        return cartRepository.findAll().stream().map(cartMapper::mapToDTO).toList();
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

    public CartDTO payCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("No cart found with id " + cartId));

        reservationService.payReservationsInCart(cartId);
        cart.setNumberOfItems(0);
        cart.setTotalPrice(0.0);

        Cart emptyCart = cartRepository.save(cart);
        return cartMapper.mapToDTO(emptyCart);
    }

    public void addReservationToCart(Long cartId, Double newReservationPrice) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new org.springframework.data.rest.webmvc.ResourceNotFoundException("No cart found with id: " + cartId));
        cart.setTotalPrice(cart.getTotalPrice() + newReservationPrice);
        cart.setNumberOfItems(cart.getNumberOfItems() + 1);
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
        cartRepository.DeleteByUserId(userId);
    }
}
