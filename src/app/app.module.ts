/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
import { APP_BASE_HREF } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { CoreModule } from './@core/core.module';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { ThemeModule } from './@theme/theme.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NbDatepickerModule } from '@nebular/theme/components/datepicker/datepicker.module';
import { NbAuthModule, NbPasswordAuthStrategy, NbAuthJWTToken } from '@nebular/auth';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,

    NgbModule.forRoot(),
    ThemeModule.forRoot(),
    CoreModule.forRoot(),
    NbDatepickerModule.forRoot(),
    NbAuthModule.forRoot({
      strategies: [
        NbPasswordAuthStrategy.setup({
          name: 'email',
          baseEndpoint: 'http://localhost:8080',
          login: {
            endpoint: '/auth/login',
            method: 'post',
            redirect: {
              success: 'pages/dashboard',
              failure: null,
            }
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
            }
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
            key: 'token'
          }
        }),
      ],
      forms: {},
    })
  ],
  bootstrap: [AppComponent],
  providers: [
    { provide: APP_BASE_HREF, useValue: '/' },
  ],
})
export class AppModule {
}
