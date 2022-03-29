import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserCredentials} from "../Entities/UserCredentials";
import {Observable} from "rxjs";
import {Token} from "../Entities/Token";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<Token> {
    return this.http.request<Token>("POST", "/auth/login", {body: new UserCredentials(email, password)});
  }
}
