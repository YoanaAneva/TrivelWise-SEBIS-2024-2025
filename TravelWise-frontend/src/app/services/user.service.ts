import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  apiUrl: string = '/api/users'

  constructor(private http: HttpClient) { }

  public getById(userId: number): Observable<User> {
    return this.http.get<User>(this.apiUrl + '/' + userId);
  }

  public createUser(user: User, profilePic: File | null): Observable<User> {
    const formData = new FormData();
    formData.append('userDTO', new Blob([JSON.stringify(user)], {type: 'application/json'}));
    console.log(profilePic)
    if(profilePic) {
      formData.append('profilePic', profilePic, profilePic.name);
    }
    return this.http.post<User>(this.apiUrl + `/registration`, formData);
  }

  public login(email: string, password: string): Observable<User>{
    return this.http.post<User>(this.apiUrl + '/login', {email, password});
  }

  public deleteUser(userId: number) {
    return this.http.delete<void>(this.apiUrl + '/' + userId);
  }
}
