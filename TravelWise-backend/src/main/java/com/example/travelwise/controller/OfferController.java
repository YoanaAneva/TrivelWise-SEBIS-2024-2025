package com.example.travelwise.controller;

import com.amazonaws.services.acmpca.model.ResourceNotFoundException;
import com.example.travelwise.dto.OfferDTO;
import com.example.travelwise.dto.UserDTO;
import com.example.travelwise.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<List<OfferDTO>> getAllOffers() {
        return new ResponseEntity<>(offerService.getAllOffers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDTO> getOfferById(@PathVariable Long id) {
        try {
            OfferDTO offerDTO = offerService.getOfferById(id);
            return new ResponseEntity<>(offerDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<OfferDTO>> getAllOffersByCategory(@PathVariable Long categoryId,
                                                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                 @RequestParam(required = false, defaultValue = "9") Integer limit) {
        return new ResponseEntity<>(offerService.getOffersByCategoryId(categoryId, page, limit), HttpStatus.OK);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<OfferDTO>> getAllOffersByDepartment(@PathVariable Long departmentId,
                                                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                 @RequestParam(required = false, defaultValue = "9") Integer limit) {
        return new ResponseEntity<>(offerService.getOffersByDepartmentId(departmentId, page, limit), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OfferDTO> createOffer(@RequestPart (required = false) List<MultipartFile> images,
                                                @RequestPart @Valid OfferDTO offerDTO) {
        return new ResponseEntity<>(offerService.createOffer(offerDTO, images), HttpStatus.CREATED);
    }
}
