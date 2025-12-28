import {Component, Input} from '@angular/core';
import {StripeService} from '../../services/stripe.service';
import {Cart} from '../../models/cart';
import {ChargeRequest} from '../../models/chargeRequest';
import {CartService} from '../../services/cart.service';
import {FormsModule} from '@angular/forms';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-stripe-payment',
  imports: [FormsModule, MatButton],
  templateUrl: './stripe-payment.component.html',
  standalone: true,
  styleUrl: './stripe-payment.component.css'
})
export class StripePaymentComponent {
  stripe: any;
  @Input() cart: Cart = {} as Cart;

  constructor(private stripeService: StripeService, private cartService: CartService) {}

  async ngOnInit() {
    this.stripe = await this.stripeService.getStripe();
  }

  createCheckoutSession() {
    const chargeRequest = new ChargeRequest
    (this.cart.totalPrice * 100, "Payment for Cart Items", 'bgn');

    this.stripeService.createCheckoutSession(chargeRequest).subscribe({
      next: (response: { sessionId: string }) => {
        if (this.stripe) {
          this.stripe.redirectToCheckout({ sessionId: response.sessionId }).then((result: { error?: { message: string } }) => {
            if (result.error) {
              console.error(result.error.message);
              alert('There was an error redirecting to checkout.');
            }
          });
        } else {
          console.error('Stripe is not initialized');
        }
      },
      error: (err) => {
        console.error('Error creating checkout session', err);
        alert('Error creating checkout session. Please try again.');
      }})
  }
}
