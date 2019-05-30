import { Injectable } from "@angular/core";
import { tap } from "rxjs/operators";
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse,
  HttpErrorResponse
} from "@angular/common/http";
import { Observable } from "rxjs/Observable";
import { environment } from './../../environments/environment';
import { NbTokenService } from "@nebular/auth";

@Injectable()
export class ServerInterceptor implements HttpInterceptor {
  
    token: string;
  
    constructor(private tokenService : NbTokenService) { 

        tokenService.get().subscribe(data => {this.token = data.getValue()});

    }
  
    //function which will be called for all http calls
  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {

    
    const updatedRequest = request.clone({
        setHeaders: {"Authorization": "Bearer " + this.token},
      url: environment.apiUrl + request.url,
    });

    return next.handle(updatedRequest);
  }
}