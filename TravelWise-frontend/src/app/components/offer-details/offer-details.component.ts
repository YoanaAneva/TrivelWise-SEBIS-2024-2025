import { Component } from '@angular/core';
import { Offer } from '../../models/offer';
import { OfferService } from '../../services/offer.service';
import {ActivatedRoute, Router} from '@angular/router';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { CarouselModule } from '@coreui/angular';
import {NgForOf, NgIf} from '@angular/common';
import {MatIcon} from '@angular/material/icon';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-offer-details',
  imports: [NavBarComponent, CarouselModule, NgForOf, NgIf, MatIcon, MatButton],
  templateUrl: './offer-details.component.html',
  standalone: true,
  styleUrl: './offer-details.component.css'
})
export class OfferDetailsComponent {
  offer: Offer = {} as Offer;
  isUserLoggedIn: boolean;

  constructor(private offerService: OfferService, private route: ActivatedRoute, private router: Router) {
    const offerId = Number(this.route.snapshot.paramMap.get('offerId'))
    offerService.getOfferById(offerId).subscribe((data) => {
      this.offer = data;
    });
    this.isUserLoggedIn = Boolean(sessionStorage.getItem('isLoggedIn'));
  }

  navigateToMakeReservation() {
    this.router.navigate(['make-reservation', this.offer.id])
  }
}
