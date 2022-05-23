import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserCredentials} from "../Entities/UserCredentials";
import {Observable} from "rxjs";
import {Token} from "../Entities/Token";
import {UserRegistration} from "../Entities/UserRegistration";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<Token> {
    return this.http.request<Token>("POST", "/auth/login", {body: new UserCredentials(email, password)});
  }

  register(userName: string, email: string, password: string, passwordConfirmation: string): Observable<any> {
    return this.http.post("/auth/register", new UserRegistration(userName, email, password, passwordConfirmation));
  }
}
