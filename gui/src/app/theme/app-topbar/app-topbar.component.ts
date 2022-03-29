import { Component, OnDestroy } from '@angular/core';
import { AppMainComponent } from '../app-main/app-main.component';
import { Subscription } from 'rxjs';
import { MenuItem } from 'primeng/api';

@Component({
    selector: 'app-topbar',
    templateUrl: './app-topbar.component.html'
})
export class AppTopbarComponent {

    items: MenuItem[];

    constructor(public appMain: AppMainComponent) { }
}
