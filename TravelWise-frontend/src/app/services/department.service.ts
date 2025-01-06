import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of, tap} from 'rxjs';
import {Department} from '../models/department';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {
  apiUrl: string = '/api/departments';
  departments: Department[] | null = null;

  constructor(private http: HttpClient) { }

  public getAll(): Observable<Department[]> {
    if (this.departments) {
      return of(this.departments); // returns cached data
    }
    return this.http.get<Department[]>(this.apiUrl).pipe(
      tap((data) => (this.departments = data)) //caches data
    );
  }
}
