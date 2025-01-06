import { Component } from '@angular/core';
import {Cart} from '../../models/cart';
import {CartService} from '../../services/cart.service';
import {Reservation} from '../../models/reservation';
import {ReservationService} from '../../services/reservation.service';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {MatDivider} from '@angular/material/divider';
import {Router} from '@angular/router';
import {StripePaymentComponent} from '../stripe-payment/stripe-payment.component';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-cart-content',
  imports: [NgForOf, MatDivider, StripePaymentComponent, NgClass, NgIf, MatButton],
  templateUrl: './cart-content.component.html',
  standalone: true,
  styleUrl: './cart-content.component.css'
})
export class CartContentComponent {
  cart: Cart = {} as Cart;
  reservations: Reservation[] = [];
  isCheckAvailPressed: boolean = false;
  canProceedToPayment: boolean = false;
  availabilityMap: Map<number, boolean> = new Map();

  constructor(private cartService: CartService, private reservationService: ReservationService,
              private router: Router) {
    if(sessionStorage.getItem('isLoggedIn') !== 'null') {
      const cartId = Number(sessionStorage.getItem('cartId'));
      this.fetchCartAndReservations(cartId);
    }
  }

  fetchCartAndReservations(cartId: number): void {
    this.cartService.getCartById(cartId).subscribe((data) => {
      this.cart = data;
    });

    this.reservationService.getByCart(cartId).subscribe((data) => {
      this.reservations = data.filter((reservation) => !reservation.paid);
    });
  }

  deleteReservationFromCart(reservationId: number) {
    this.canProceedToPayment = false;
    this.reservationService.deleteReservation(reservationId).subscribe({
      next: () => {
        this.cartService.getCartById(this.cart.id!).subscribe((data) =>{
          this.cart = data;
        })
        this.reservations = this.reservations.filter(reservation => reservation.id !== reservationId);
      },
      error: () => {}
    });
  }

  checkForAvailability() {
    this.cartService.checkAvailability(this.cart.id!).subscribe((availabilityMap) => {
      this.availabilityMap = availabilityMap;
      const hasFalseValue = Array.from(availabilityMap.values()).some(value => value === false);
      this.isCheckAvailPressed = true;
      this.canProceedToPayment = !hasFalseValue;
    })
  }

  navigateToOfferDetails(offerId: number) {
    this.router.navigate(['offer-details', offerId])
  }
}
