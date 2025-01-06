import { Component } from '@angular/core';
import {Offer} from '../../models/offer';
import {OfferService} from '../../services/offer.service';
import {ReservationService} from '../../services/reservation.service';
import {MatGridList, MatGridTile} from '@angular/material/grid-list';
import {NgForOf, NgIf} from '@angular/common';
import {OfferCardComponent} from '../offer-card/offer-card.component';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-recommendations-list',
  imports: [MatGridList, MatGridTile, NgForOf, OfferCardComponent, MatIcon, NgIf],
  templateUrl: './recommendations-list.component.html',
  standalone: true,
  styleUrl: './recommendations-list.component.css'
})
export class RecommendationsListComponent {
  recommendedOffers: Offer[] = [];
  offerIds: number[] = [];
  currentOfferIdIndex: number = 0;
  numberOfLastReserved: number = 2;

  constructor(private offerService: OfferService, private reservationService: ReservationService) {
    this.getLastReservedOfferIds();
  }

  getLastReservedOfferIds() {
    if(sessionStorage.getItem('isLoggedIn') !== 'null') {
      const cartId = Number(sessionStorage.getItem('cartId'));
      this.reservationService.getLastNReservedOfferIdsByCart(cartId, this.numberOfLastReserved)
        .subscribe((data) => {
        this.offerIds = data;
        this.getRecommendedOffers(this.offerIds[0]);
      })
    }
  }

  getRecommendedOffers(offerId: number) {
    this.offerService.getRecommendedOffers(offerId).subscribe((data) => {
      this.recommendedOffers = data;
    })
  }

  getNextPageOfRecommendations() {
    this.currentOfferIdIndex = this.currentOfferIdIndex + 1;
    this.getRecommendedOffers(this.offerIds[this.currentOfferIdIndex]);
  }

  getPreviousPageOfRecommendations() {
    this.currentOfferIdIndex = this.currentOfferIdIndex - 1;
    this.getRecommendedOffers(this.offerIds[this.currentOfferIdIndex]);
  }
}
