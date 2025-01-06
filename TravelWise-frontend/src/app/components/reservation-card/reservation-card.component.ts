import {Component, Input} from '@angular/core';
import {Reservation} from '../../models/reservation';
import {MatCardModule} from '@angular/material/card';
import {NgForOf} from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-reservation-card',
  imports: [MatCardModule, NgForOf],
  templateUrl: './reservation-card.component.html',
  standalone: true,
  styleUrl: './reservation-card.component.css'
})
export class ReservationCardComponent {
  @Input() reservation: Reservation = {} as Reservation;

  constructor(private router: Router) {
  }

  navigateToOfferDetails() {
    this.router.navigate(['offer-details', this.reservation.offerId])
  }
}
