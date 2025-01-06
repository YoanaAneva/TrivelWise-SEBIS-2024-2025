import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Reservation} from '../models/reservation';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  apiUrl: string = '/api/reservations'

  constructor(private http: HttpClient) { }

  getByCart(cartId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(this.apiUrl + '/cart/' + cartId);
  }

  getByUser(userId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(this.apiUrl + '/user/' + userId);
  }

  createReservation(reservation: Reservation): Observable<Reservation> {
    return this.http.post<Reservation>(this.apiUrl, reservation);
  }

  deleteReservation(reservationId: number){
    return this.http.delete<void>(this.apiUrl + '/' + reservationId);
  }

  getLastNReservedOfferIdsByCart(cartId: number, n: number) {
    return this.http.get<number[]>(this.apiUrl + '/cart/' + cartId + '/last/' + n);
  }
}
