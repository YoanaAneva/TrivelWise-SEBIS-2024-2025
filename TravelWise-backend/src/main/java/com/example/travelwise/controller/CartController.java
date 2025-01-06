package com.example.travelwise.controller;

import com.amazonaws.services.acmpca.model.ResourceNotFoundException;
import com.example.travelwise.dto.CartDTO;
import com.example.travelwise.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartDTO>> getCarts() {
        return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable("id") Long id) {
        try{
            return new ResponseEntity<>(cartService.getCartDTOById(id), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable("userId") Long userId) {
        try{
            return new ResponseEntity<>(cartService.getCartByUser(userId), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<CartDTO> createCart(@RequestBody CartDTO cartDTO) {
        return new ResponseEntity<>(cartService.createCart(cartDTO), HttpStatus.CREATED);
    }

    @PostMapping("/availability/{cartId}")
    public ResponseEntity<Map<Long, Boolean>> checkForAvailability(@PathVariable("cartId") Long cartId) {
        return new ResponseEntity<>(cartService.checkAvailability(cartId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> payCart(@PathVariable Long id) {
        try {
            CartDTO emptyCartDTO = cartService.processCartPayment(id);
            return new ResponseEntity<>(emptyCartDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.CONFLICT);
        }
    }
}
