import { Injectable } from '@angular/core';
import {UserService} from "./user.service";
import {Observable} from "rxjs";
import {Token} from "../Entities/Token";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(private userService: UserService) { }

  saveToken(newToken: string) {
    localStorage.setItem('token', newToken);
  }

  getToken(): string {
    return localStorage.getItem('token');
  }

  isAuthenticated(): boolean {
    let token = localStorage.getItem('token');
    return token !== null && token.length !== 0;
  }
}
