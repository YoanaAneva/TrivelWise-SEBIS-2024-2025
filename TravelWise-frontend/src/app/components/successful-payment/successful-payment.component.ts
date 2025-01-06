import { Component } from '@angular/core';
import {MatButton} from '@angular/material/button';
import {CartService} from '../../services/cart.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-successful-payment',
  imports: [MatButton],
  templateUrl: './successful-payment.component.html',
  standalone: true,
  styleUrl: './successful-payment.component.css'
})
export class SuccessfulPaymentComponent {

  constructor(private cartService: CartService, private router: Router) {}

  ngOnInit() {
    this.emptyCart();
  }

  emptyCart() {
    const cartId = Number(sessionStorage.getItem('cartId'));
    this.cartService.emptyCart(cartId).subscribe({
      next: (cart) => {},
      error: (err) => {
        if (err.status === 409) {
          alert(err.error + ' Please remove the reservation from cart')
        }
      }
    })
  }

  navigateToUserProfile() {
    this.router.navigate(['user-details', Number(sessionStorage.getItem('userId'))])
  }

}
