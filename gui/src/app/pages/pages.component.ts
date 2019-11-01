import { Component, OnInit } from '@angular/core';

import { MENU_ITEMS } from './pages-menu';
import { CollectionService } from '../Services/collection.service';
import { NbMenuItem } from '@nebular/theme';

@Component({
  selector: 'ngx-pages',
  template: `
    <ngx-one-column-layout>
      <nb-menu [items]="menu"></nb-menu>
      <router-outlet></router-outlet>
    </ngx-one-column-layout>
  `,
})
export class PagesComponent implements OnInit {

  menu = MENU_ITEMS;

  constructor(private collectionService: CollectionService) { }

  ngOnInit() {

    this.collectionService.getUserCollections().subscribe(collections => {

      for (const collection of collections ) {
        const newItem = new NbMenuItem();
        newItem.title = collection.name;
        newItem.link = '/pages/collection/view/' + collection.id;
        newItem.icon = 'grid-outline';

        this.menu.push(newItem);
      }
    });
  }
}
