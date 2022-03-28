import { APP_BASE_HREF } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule, Injectable } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { ServerInterceptor } from './Interceptors/server-interceptor';
import { RatingModule } from 'ng-starrating';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { PagesModule } from './pages/pages.module';
import { AppRoutingModule } from './app-routing.module';
import { of as observableOf } from 'rxjs';
import {CalendarModule} from "primeng/calendar";
import {ConfigService} from "./Services/app.config.service";
import {ConfirmationService} from "primeng/api";
import {MenuService} from "./Services/app.menu.service";


@NgModule({
  declarations: [AppComponent],
  imports: [
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    PagesModule,
    FormsModule,
    ReactiveFormsModule,
    RatingModule,
    NgbModule,
    CalendarModule,
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production }),
  ],
  bootstrap: [AppComponent],
  providers: [
    { provide: APP_BASE_HREF, useValue: '/' },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ServerInterceptor,
      multi: true,
    },
    ConfigService,
    ConfirmationService,
    MenuService
  ],
})
export class AppModule { }
