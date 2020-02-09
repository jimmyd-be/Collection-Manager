import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Role } from '../Entities/Role';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RoleService {

  constructor(private http: HttpClient) {}

  getActiveRoles(): Observable<Role[]> {
    return this.http.get<Role[]>('/role/active');
  }
}
