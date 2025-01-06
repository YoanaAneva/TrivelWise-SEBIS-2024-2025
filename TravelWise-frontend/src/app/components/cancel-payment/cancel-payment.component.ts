import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-cancel-payment',
  imports: [MatButton],
  templateUrl: './cancel-payment.component.html',
  standalone: true,
  styleUrl: './cancel-payment.component.css'
})
export class CancelPaymentComponent {

  constructor(private router: Router) {
  }

  navigateToUserProfile() {
    this.router.navigate(['user-details', Number(sessionStorage.getItem('userId'))])
  }
}
