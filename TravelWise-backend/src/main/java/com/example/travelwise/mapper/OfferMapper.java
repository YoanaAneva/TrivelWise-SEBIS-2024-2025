package com.example.travelwise.mapper;

import com.example.travelwise.dto.OfferDTO;
import com.example.travelwise.entity.Image;
import com.example.travelwise.entity.Offer;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OfferMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "images", target = "imageUrls")
    OfferDTO mapToDTO(Offer offer);

    @Mapping(source = "categoryId", target = "category.id")
    Offer mapToEntity(OfferDTO offerDTO);

    @IterableMapping(qualifiedByName = "mapImageToUrl")
    List<String> mapImagesToUrls(List<Image> images);

    @Named("mapImageToUrl")
    default String mapImageToUrl(Image image) {
        return image != null ? image.getUrl() : null;
    }
}
