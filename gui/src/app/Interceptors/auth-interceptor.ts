import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpResponse, HttpErrorResponse,
} from '@angular/common/http';
import {finalize, Observable, tap} from 'rxjs';
import { environment } from './../../environments/environment';
import {TokenService} from "../Services/token.service";
import {Router} from "@angular/router";
import {map} from "rxjs/operators";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {


  constructor(private router: Router) {
  }

  intercept(request: HttpRequest<any>,
            next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(request).pipe(
      tap({
        error: (error) => {
          if (error instanceof HttpErrorResponse) {
            if (error.status === 401) {
              this.router.navigate(['/auth/login']);
            }
          }
        },
      }));
  }
}
