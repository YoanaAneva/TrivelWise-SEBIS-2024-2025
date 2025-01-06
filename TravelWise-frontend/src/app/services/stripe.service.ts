import { Injectable } from '@angular/core';
import {loadStripe, Stripe} from '@stripe/stripe-js';
import {HttpClient} from '@angular/common/http';
import {ChargeRequest} from '../models/ChargeRequest';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StripeService {
  stripePromise: Promise<Stripe | null>;
  apiUrl: string = '/api/stripe-payment';
  stripePublicKey: string = 'pk_test_51QbNliJqAb4TAZPbuWn5vfK16Jk7p4PEO7tONtXRbOp6xKxvYmzIRSalDZDx8QZfqQ43MyaDHcQhl49wBNsi6iWZ00dPoD9Apv';


  constructor(private http: HttpClient) {
    this.stripePromise = loadStripe(this.stripePublicKey);
  }

  getStripe() {
    return this.stripePromise;
  }

  createCheckoutSession(chargeRequest: ChargeRequest): Observable<any> {
    return this.http.post<any>(this.apiUrl + '/create-checkout-session', chargeRequest);
  }
}
