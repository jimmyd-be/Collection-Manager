import { APP_BASE_HREF } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule, Injectable } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ServerInterceptor } from './Interceptors/server-interceptor';
import { RatingModule } from 'ng-starrating';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { PagesModule } from './pages/pages.module';
import { AppRoutingModule } from './app-routing.module';
import {CalendarModule} from "primeng/calendar";
import {ConfigService} from "./Services/app.config.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {MenuService} from "./Services/app.menu.service";
import {ThemeModule} from "./theme/theme.module";
import {DialogService} from "primeng/dynamicdialog";
import {AuthInterceptor} from "./Interceptors/auth-interceptor";


@NgModule({
  declarations: [AppComponent],
  imports: [
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    PagesModule,
    ThemeModule,
    FormsModule,
    ReactiveFormsModule,
    RatingModule,
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
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    ConfigService,
    ConfirmationService,
    MenuService,
    MessageService,
    DialogService
  ],
})
export class AppModule { }
