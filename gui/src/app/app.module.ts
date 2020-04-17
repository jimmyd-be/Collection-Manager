import { APP_BASE_HREF } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CoreModule } from './@core/core.module';

import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { ThemeModule } from './@theme/theme.module';
import { NbAuthModule, NbPasswordAuthStrategy, NbAuthJWTToken } from '@nebular/auth';
import { ServerInterceptor } from './Interceptors/server-interceptor';
import { RatingModule } from 'ng-starrating';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { NbDatepickerModule, NbMenuModule, NbSidebarService } from '@nebular/theme';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { PagesModule } from './pages/pages.module';
import { AppRoutingModule } from './app-routing.module';



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
    ThemeModule.forRoot(),
    //CoreModule.forRoot(),
    
    NbDatepickerModule.forRoot(),
    NbMenuModule.forRoot(),
    NbAuthModule.forRoot({
      strategies: [
        NbPasswordAuthStrategy.setup({
          name: 'email',
          baseEndpoint: '',
          login: {
            endpoint: '/auth/login',
            method: 'post',
            redirect: {
              success: 'pages/dashboard',
              failure: null,
            },
          },
          register: {
            endpoint: '/auth/register',
            method: 'post',
          },
          logout: {
            endpoint: '/auth/logout',
            method: 'post',
            redirect: {
              success: '/',
              failure: '/',
            },
          },
          requestPass: {
            endpoint: '/auth/request-pass',
            method: 'post',
          },
          resetPass: {
            endpoint: '/auth/reset-pass',
            method: 'post',
          },
          token: {
            class: NbAuthJWTToken,
            key: 'token',
          },
        }),
      ],
      forms: {},
    }),
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
    NbSidebarService,
  ],
})
export class AppModule { }
