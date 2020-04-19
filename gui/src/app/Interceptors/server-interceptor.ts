import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from './../../environments/environment';
import { NbTokenService } from '@nebular/auth';

@Injectable()
export class ServerInterceptor implements HttpInterceptor {

    token: string;

    constructor(private tokenService: NbTokenService) {
        this.tokenService.tokenChange().subscribe(data => {this.token = data.getValue(); });
    }

  intercept(  request: HttpRequest<any>,
              next: HttpHandler): Observable<HttpEvent<any>> {

    const updatedRequest = request.clone({
        setHeaders: {Authorization: 'Bearer ' + this.token},
      url: environment.apiUrl + request.url,
    });

    return next.handle(updatedRequest);
  }
}
