import { Component, OnInit } from '@angular/core';
import { CollectionService } from '../../Services/collection.service';
import { Collection } from '../../Entities/collection';

@Component({
  selector: 'add-item-manually',
  templateUrl: './add-item-manually.component.html',
  styleUrls: ['./add-item-manually.component.scss']
})
export class AddItemManuallyComponent implements OnInit {

collectionList: Collection[];

  constructor(private collectionService : CollectionService) { }

  ngOnInit() {

    this.collectionList = this.collectionService.getUserCollections();
  }


  collectionSelectionChanged(event){
    console.log(event);

  }
}
