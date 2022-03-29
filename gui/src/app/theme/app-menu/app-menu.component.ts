import {Component, OnInit} from '@angular/core';
import {AppMainComponent} from '../app-main/app-main.component';

@Component({
  selector: 'app-menu',
  templateUrl: './app-menu.component.html',
})
export class AppMenuComponent implements OnInit {

  model: any[];

  constructor(public appMain: AppMainComponent) {
  }

  ngOnInit() {
    this.model = [
      {
        label: 'Home',
        items: [
          {label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: ['/']},
          {label: 'Add Collection', icon: 'pi pi-fw pi-plus', routerLink: ['/pages/collection/add']}
        ]
      },
      {
        label: 'Add Items',
        items: [
          {label: 'Manual', icon: 'pi pi-fw pi-pencil', routerLink: ['/pages/item/addManual']},
          {label: 'Search', icon: 'pi pi-fw pi-search', routerLink: ['/pages/item/addExternally']}
        ]
      },
      {
        label: 'Admin',
        items: [
          {label: 'Settings', icon: 'pi pi-fw pi-cog', routerLink: ['/pages/admin/settings']},
          {label: 'Users', icon: 'pi pi-fw pi-users', routerLink: ['/pages/admin/users']},
        ]
      },
      {
        label: 'Collections',
        items: [
          {label: 'PrimeIcons', icon: 'pi pi-fw pi-prime', routerLink: ['/icons']},
          {label: 'PrimeFlex', icon: 'pi pi-fw pi-desktop', url: ['https://www.primefaces.org/primeflex/']},
        ]
      }
    ];
  }

  onKeydown(event: KeyboardEvent) {
    const nodeElement = (<HTMLDivElement>event.target);
    if (event.code === 'Enter' || event.code === 'Space') {
      nodeElement.click();
      event.preventDefault();
    }
  }
}
