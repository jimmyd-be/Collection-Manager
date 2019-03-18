import { Component, OnInit } from '@angular/core';
import { CollectionService } from '../../Services/collection.service';
import { Collection } from '../../Entities/collection';
import { CustomFieldService } from '../../Services/custom-field.service';

@Component({
  selector: 'add-item-manually',
  templateUrl: './add-item-manually.component.html',
  styleUrls: ['./add-item-manually.component.scss']
})
export class AddItemManuallyComponent implements OnInit {

collectionList: Collection[];

  constructor(private collectionService : CollectionService, private customfieldService: CustomFieldService) { }

  ngOnInit() {

    this.collectionList = this.collectionService.getUserCollections();
  }


  collectionSelectionChanged(collectionId : Number){
    
    this.customfieldService.getFieldsByCollection(collectionId);
  }
}
