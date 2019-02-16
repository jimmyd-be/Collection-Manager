import { Component } from '@angular/core';
import { Collection } from '../../Entities/collection';
import { CollectionService } from '../../Services/collection.service';
import { faPen, faList, faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'ngx-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  collections: Collection[];
  viewIcon = faList;
  editIcon = faPen;
  removeIcon = faTrash;

  constructor(private collectionService: CollectionService) { }

  ngOnInit() {

    this.collections = this.collectionService.getUserCollections();
  }
}
