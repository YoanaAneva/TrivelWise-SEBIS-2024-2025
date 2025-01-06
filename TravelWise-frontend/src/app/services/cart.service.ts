import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {Cart} from '../models/cart';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  apiUrl: string = '/api/carts';

  constructor(private http: HttpClient) { }

  public getCartById(cartId: number): Observable<Cart> {
    return this.http.get<Cart>(this.apiUrl + '/' + cartId);
  }

  public emptyCart(cartId: number): Observable<Cart> {
    return this.http.put<Cart>(this.apiUrl + '/' + cartId, null);
  }

  public checkAvailability(cartId: number): Observable<any> {
    return this.http.post<any>(this.apiUrl + '/availability/' + cartId, null).pipe(
      map((response: any) => {
        return new Map(Object.entries(response).map(([key, value]) => [Number(key), value]));
      })
    );
  }
}
