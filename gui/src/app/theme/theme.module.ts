import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AppFooterComponent} from "./app-footer/app-footer.component";
import {AppConfigComponent} from "./app-config/app-config.component";
import {AppMainComponent} from "./app-main/app-main.component";
import {AppMenuitemComponent} from "./app-menuitem/app-menuitem.component";
import {AppTopbarComponent} from "./app-topbar/app-topbar.component";
import {RadioButtonModule} from "primeng/radiobutton";
import {RouterModule} from "@angular/router";
import {InputSwitchModule} from "primeng/inputswitch";
import {AppMenuComponent} from "./app-menu/app-menu.component";
import {FormsModule} from "@angular/forms";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {ToastModule} from "primeng/toast";



@NgModule({
  declarations: [
    AppFooterComponent,
    AppConfigComponent,
    AppMainComponent,
    AppMenuitemComponent,
    AppTopbarComponent,
    AppMenuComponent],
  exports: [

  ],
    imports: [
        CommonModule,
        RadioButtonModule,
        RouterModule,
        InputSwitchModule,
        FormsModule,
        ConfirmDialogModule,
        ToastModule
    ]
})
export class ThemeModule { }
