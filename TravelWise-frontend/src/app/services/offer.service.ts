import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Offer} from '../models/offer';
import {Observable, of, tap} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OfferService {
  apiUrl: string = '/api/offers'
  recommendationsCache: Map<number, Offer[]> = new Map();

  constructor(private http: HttpClient) { }

  getOfferById(id: number): Observable<Offer> {
    return this.http.get<Offer>(this.apiUrl + '/' + id);
  }

  getOffersByCategory(categoryId: number, currentPage: number, pageSize: number) {
    let params = new HttpParams();
    if(currentPage !== undefined && currentPage !== null && currentPage >= 0) {
      params = params.set('page', currentPage.toString());
    }
    if(pageSize !== undefined && pageSize !== null && pageSize >= 0) {
      params = params.set('limit', pageSize.toString());
    }
    return this.http.get<Offer[]>(this.apiUrl + '/category/available/' + categoryId, { params });
  }

  getRecommendedOffers(offerId: number) {
    if (this.recommendationsCache.has(offerId)) {
      return of(this.recommendationsCache.get(offerId)!);
    }
    return this.http.get<Offer[]>(`${this.apiUrl}/recommendations/${offerId}`).pipe(
      tap((recommendations) => {
        this.recommendationsCache.set(offerId, recommendations);
      })
    );
  }

  seachOfferByTitle(title: string, currentPage: number, pageSize: number): Observable<Offer[]> {
    let params = new HttpParams();
    params = params.set('title', title);
    if(currentPage !== undefined && currentPage !== null && currentPage >= 0) {
      params = params.set('page', currentPage.toString());
    }
    if(pageSize !== undefined && pageSize !== null && pageSize >= 0) {
      params = params.set('limit', pageSize.toString());
    }
    console.log(params);
    return this.http.get<Offer[]>(this.apiUrl + '/search', { params });
  }
}
