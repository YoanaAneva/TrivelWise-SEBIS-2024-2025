package com.example.travelwise.service;

import com.example.travelwise.dto.OfferDTO;
import com.example.travelwise.entity.Image;
import com.example.travelwise.entity.Offer;
import com.example.travelwise.mapper.OfferMapper;
import com.example.travelwise.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final ImageService imageService;

    @Autowired
    public OfferService(OfferRepository offerRepository, OfferMapper offerMapper, ImageService imageService) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.imageService = imageService;
    }

    public List<OfferDTO> getAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(offerMapper::mapToDTO)
                .toList();
    }

    public OfferDTO getOfferById(Long offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException("No offer with id: " + offerId));
        return offerMapper.mapToDTO(offer);
    }

    public List<OfferDTO> getOffersByCategoryId(Long categoryId, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return offerRepository.findAllByCategoryId(categoryId, pageable)
                .stream()
                .map(offerMapper::mapToDTO)
                .toList();
    }

    public List<OfferDTO> getAvailableOffersByCategoryId(Long categoryId, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return offerRepository.findByCategoryIdAndAvailableSpotsGreaterThan(categoryId, 0, pageable)
                .stream()
                .map(offerMapper::mapToDTO)
                .toList();
    }

    public List<OfferDTO> getOffersByDepartmentId(Long departmentId, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return offerRepository.findAllByCategoryDepartmentId(departmentId, pageable)
                .stream()
                .map(offerMapper::mapToDTO)
                .toList();
    }

    public OfferDTO createOffer(OfferDTO offerDTO, List<MultipartFile> images) {
        Offer newOffer = offerMapper.mapToEntity(offerDTO);
        newOffer = offerRepository.save(newOffer);
        if (images != null) {
            for (MultipartFile image : images) {
                imageService.addImageToOffer(image, newOffer);
            }
        }
        return offerMapper.mapToDTO(newOffer);
    }

    public void deleteOfferById(Long offerId) {
        Offer offerToDelete = offerRepository.findById(offerId).orElse(null);
        if(offerToDelete != null) {
            List<Image> offerImages = offerToDelete.getImages();
            for (Image image : offerImages) {
                imageService.deleteFile(image.getUrl());
            }
        }
        offerRepository.deleteById(offerId);
    }

    public List<OfferDTO> getRecommendedOffers(Long offerId, Integer limitCount) {
        return offerRepository.getRecommendedOffers(offerId, limitCount)
                .stream()
                .map(offerMapper::mapToDTO)
                .toList();
    }

    public List<OfferDTO> searchOffersByTitle(String title, Integer page, Integer limitCount) {
        Pageable pageable = PageRequest.of(page, limitCount, Sort.by("title").ascending());
        return offerRepository.findByTitleContainingIgnoreCase(title, pageable)
                .stream()
                .map(offerMapper::mapToDTO)
                .toList();
    }
}
