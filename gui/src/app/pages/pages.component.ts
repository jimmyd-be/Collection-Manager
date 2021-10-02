import {Component, OnInit} from '@angular/core';

import {MENU_ITEMS} from './pages-menu';
import {CollectionService} from '../Services/collection.service';
import {NbMenuItem} from '@nebular/theme';
import {UserService} from '../Services/user.service';

@Component({
  selector: 'app-pages',
  template: `
    <app-one-column-layout>
      <nb-menu [items]="menu"></nb-menu>
      <router-outlet></router-outlet>
    </app-one-column-layout>
  `,
})
export class PagesComponent implements OnInit {

  menu = MENU_ITEMS;
  originalArray = [];


  constructor(private collectionService: CollectionService, private userService: UserService) {
  }

  ngOnInit() {

    const isAdmin = this.userService.getUser().toPromise().then(user => {
      return user.admin;
    });

    if (!isAdmin) {
      this.menu.forEach((item, index) => {
        if (item.title === 'Admin') {
          this.menu.splice(index, 1);
        }
      });
    }

    this.menu.forEach(val => this.originalArray.push(Object.assign({}, val)));


    this.collectionService.getUserCollections().subscribe(collections => {

      this.menu = [];
      this.originalArray.forEach(val => this.menu.push(Object.assign({}, val)));

      for (const collection of collections) {
        const newItem = new NbMenuItem();
        newItem.title = collection.name;
        newItem.link = '/pages/collection/view/' + collection.id;

        if (collection.type === 'Books') {
          newItem.icon = 'book-outline';
        } else if (collection.type === 'Disks') {
          newItem.icon = 'music-outline';
        } else if (collection.type === 'Movies') {
          newItem.icon = 'film-outline';
        } else if (collection.type === 'Magazines') {
          newItem.icon = 'map-outline';
        } else if (collection.type === 'Comics') {
          newItem.icon = 'layers-outline';
        } else if (collection.type === 'Games') {
          newItem.icon = 'tv-outline';
        } else {
          newItem.icon = 'grid-outline';
        }

        this.menu.push(newItem);
      }
    });

  }
}
