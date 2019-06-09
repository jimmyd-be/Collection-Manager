import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../Entities/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUser(): Observable<User> {
    return this.http.get<User>('/user');
  }

}
