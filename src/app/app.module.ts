import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MenuComponent } from './menu/menu.component';
import { HomeComponent } from './home/home.component';
import { AddCollectionComponent } from './add-collection/add-collection.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AddCustomFieldComponent } from './add-collection/add-custom-field/add-custom-field.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';



@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    HomeComponent,
    AddCollectionComponent,
    AddCustomFieldComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
