import {Component, Input} from '@angular/core';
import { Offer } from '../../models/offer';
import {RouterLink} from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-offer-card',
  imports: [MatCardModule, RouterLink, NgClass],
  templateUrl: './offer-card.component.html',
  standalone: true,
  styleUrl: './offer-card.component.css'
})
export class OfferCardComponent {
  @Input() offer: Offer = {} as Offer;

  constructor() {
  }
}
