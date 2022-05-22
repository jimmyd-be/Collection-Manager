import {Component, OnInit} from '@angular/core';
import {AppMainComponent} from '../app-main/app-main.component';
import {CollectionService} from "../../Services/collection.service";
import {MenuItem} from 'primeng/api';
import {Collection} from "../../Entities/collection";


@Component({
  selector: 'app-menu',
  templateUrl: './app-menu.component.html',
})
export class AppMenuComponent implements OnInit {

  model: any[];

  constructor(private collectionService: CollectionService) {
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
      }
    ];



    this.collectionService.getUserCollections()
      .subscribe(collections => {

        let collectionMenuItems: MenuItem = {
          label: "Collections",
          items: this.getItemByCollections(collections)
        };
        this.model.push(collectionMenuItems);
      })

  }

  private getItemByCollections(collections: Collection[]) {

    let items = [];

    collections.forEach(collection => {
      let item: MenuItem = {
        label: collection.name,
        icon: this.getIcon(collection.type),
        routerLink: ['/pages/collection/view/' + collection.id]
      };

      items.push(item);
    });


    return items;
  }

  private getIcon(type: string) {

    switch (type){
      case "Books": return "pi pi-fw pi-book";
      case "Games":
      case "Movies":
      case "Comics":
      case "Magazines":
    }

    return "";
  }

  onKeydown(event: KeyboardEvent) {
    const nodeElement = (<HTMLDivElement>event.target);
    if (event.code === 'Enter' || event.code === 'Space') {
      nodeElement.click();
      event.preventDefault();
    }
  }
}
