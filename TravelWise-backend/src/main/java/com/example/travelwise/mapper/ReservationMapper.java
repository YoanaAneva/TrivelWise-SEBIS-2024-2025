package com.example.travelwise.mapper;

import com.example.travelwise.dto.ReservationDTO;
import com.example.travelwise.entity.Offer;
import com.example.travelwise.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    @Mapping(source = "offer.id", target = "offerId")
    @Mapping(source = "offer.title", target = "offerTitle")
    @Mapping(source = "offer", target = "offerImageUrl", qualifiedByName = "mapOfferFirstImageUrl")
    @Mapping(source = "cart.id", target = "cartId")
//    @Mapping(source = "order.id", target = "orderId")
    ReservationDTO mapToDTO(Reservation reservation);
    @Mapping(source = "offerId", target = "offer.id")
    @Mapping(source = "cartId", target = "cart.id")
//    @Mapping(source = "orderId", target = "order.id")
    Reservation mapToEntity(ReservationDTO reservationDTO);

    default String mapOfferFirstImageUrl(Offer offer) {
        if (offer != null && offer.getImages() != null && !offer.getImages().isEmpty()) {
            return offer.getImages().get(0).getUrl();
        }
        return null;
    }
}
