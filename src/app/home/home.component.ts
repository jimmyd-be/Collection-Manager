import { Component, OnInit } from '@angular/core';
import { Collection } from '../Entities/collection';
import { CollectionService } from '../Services/collection.service';
import { faPen, faList, faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  collections: Collection[];
  viewIcon = faList;
  editIcon = faPen;
  removeIcon = faTrash;

  constructor(private collectionService: CollectionService) { }

  ngOnInit() {

    this.collections = this.collectionService.getUserCollections();
  }

}
