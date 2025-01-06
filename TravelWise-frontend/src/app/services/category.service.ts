import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of, tap} from 'rxjs';
import {Category} from '../models/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  apiUrl: string = '/api/categories';
  private categories: Category[] | null = null;

  constructor(private http: HttpClient) { }

  public getAll(): Observable<Category[]> {
    if(this.categories) {
      return of(this.categories);
    }
    return this.http.get<Category[]>(this.apiUrl).pipe(
      tap((data) => (this.categories = data))
    );
  }

  public getById(categoryId: number): Observable<Category> {
    return this.http.get<Category>(this.apiUrl + '/' + categoryId);
  }
}
