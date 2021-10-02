import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../Entities/user';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>('/admin/users');
  }
}
