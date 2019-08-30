import { Component, OnInit } from '@angular/core';
import { Collection } from '../../Entities/collection';
import { CollectionService } from '../../Services/collection.service';

@Component({
  selector: 'ngx-share-collection',
  templateUrl: './share-collection.component.html',
  styleUrls: ['./share-collection.component.scss'],
})
export class ShareCollectionComponent implements OnInit {
  
  collectionList: Collection[];

  constructor(private collectionService: CollectionService) { }

  ngOnInit() {

    this.collectionService.getUserCollections().subscribe(data => {
      this.collectionList = data;
    });
  }

}
