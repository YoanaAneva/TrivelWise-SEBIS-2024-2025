import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormArray,
  ReactiveFormsModule,
  Validators,
  ValidatorFn,
  AbstractControl, ValidationErrors
} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';
import {MatFormField} from '@angular/material/form-field';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {Offer} from '../../models/offer';
import {OfferService} from '../../services/offer.service';
import {ActivatedRoute, Router} from '@angular/router';
import {NavBarComponent} from '../nav-bar/nav-bar.component';
import {TravelerDetails} from '../../models/travelerDetails';
import {Reservation} from '../../models/reservation';
import {ReservationService} from '../../services/reservation.service';

@Component({
  selector: 'app-make-reservation',
  imports: [NgForOf, ReactiveFormsModule, MatFormField, MatFormFieldModule,
    MatInput, MatButton, NavBarComponent, NgIf],
  templateUrl: './make-reservation.component.html',
  standalone: true,
  styleUrl: './make-reservation.component.css'
})
export class MakeReservationComponent {
  offer: Offer = {} as Offer;
  totalPrice: number = 0;
  travelerForm = new FormGroup({
    travelers: new FormArray([]),
  });

  constructor(private reservationService: ReservationService, private offerService: OfferService,
              private router: Router, private route: ActivatedRoute) {
    const offerId = Number(this.route.snapshot.paramMap.get('offerId'))
    this.offerService.getOfferById(offerId).subscribe((data) => {
      this.offer = data;
    })
  }

  get travelers() {
    return this.travelerForm.get('travelers') as FormArray;
  }

  addTraveler() {
    if (this.travelerForm.valid && this.travelers.length < this.offer.availableSpots) {
      this.travelers.push(
        new FormGroup({
          firstName: new FormControl(this.travelers.at(this.travelers.length - 1)?.get('firstName')?.value || '', [Validators.required]),
          surname: new FormControl(this.travelers.at(this.travelers.length - 1)?.get('surname')?.value || '', [Validators.required]),
          email: new FormControl(this.travelers.at(this.travelers.length - 1)?.get('email')?.value || '', [Validators.required, Validators.email]),
          phoneNumber: new FormControl(this.travelers.at(this.travelers.length - 1)?.get('phoneNumber')?.value || '', [Validators.required, this.phoneValidator()]),
        })
      );

      this.travelers.at(this.travelers.length - 2)?.reset();
      this.totalPrice += this.offer.price;
    }
  }

  removeTraveler(index: number) {
    this.travelers.removeAt(index);
  }

  onSubmit() {
    if (this.travelers.length <= 0) {
      return;
    }
    if (this.travelerForm.valid) {
      let travelersDetails: TravelerDetails[] = [];
      this.travelers.controls.forEach((traveler) => {
          let newTravelerDetails = new TravelerDetails(
            traveler.get('firstName')?.value, traveler.get('surname')?.value,
              traveler.get('email')?.value, traveler.get('phoneNumber')?.value);
        travelersDetails.push(newTravelerDetails);
      });

      const cartId: number = Number(sessionStorage.getItem('cartId'));
      let newReservation = new Reservation(this.generateRandomString(),
        this.totalPrice, this.formatDateToString(new Date()), this.offer.id!,
        cartId, travelersDetails)

      this.reservationService.createReservation(newReservation).subscribe((data) =>
      console.log(data));
    }
    this.router.navigate(['user-details', Number(sessionStorage.getItem('userId'))])
  }

  generateRandomString(): string {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let result = '';
    for (let i = 0; i < 10; i++) {
      result += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return result;
  }

  phoneValidator() : ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const phoneRegex = /^\+?[1-9]\d{1,14}$/;
      const valid = phoneRegex.test(control.value);
      if(!valid) {
        control.get('phoneNumber')?.setErrors({ invalidPhone: true });
        return { invalidPhone: true };
      }
      return null;
    };
  }

  formatDateToString(date: Date) {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
}
