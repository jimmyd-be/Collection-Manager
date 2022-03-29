import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from './../../environments/environment';
import {TokenService} from "../Services/token.service";

@Injectable()
export class ServerInterceptor implements HttpInterceptor {


  constructor(private tokenService: TokenService) {
    //this.tokenService.tokenChange().subscribe(data => { this.token = data.getValue(); });
  }

  intercept(request: HttpRequest<any>,
            next: HttpHandler): Observable<HttpEvent<any>> {

    let token = this.tokenService.getToken();

    if (token != null && token.length > 0) {
      const updatedRequest = request.clone({
        setHeaders: { Authorization: 'Bearer ' + token },
        url: environment.apiUrl + request.url,
      });
      return next.handle(updatedRequest);
    } else {
      const updatedRequest = request.clone({
        url: environment.apiUrl + request.url,
      });
      return next.handle(updatedRequest);
    }
  }
}
