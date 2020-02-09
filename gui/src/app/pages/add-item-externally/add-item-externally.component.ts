import { Component, OnInit } from '@angular/core';
import { Collection } from '../../Entities/collection';
import { CollectionService } from '../../Services/collection.service';
import { ItemService } from '../../Services/item.service';
import { ItemSearch } from '../../Entities/ItemSearch';

@Component({
  selector: 'ngx-add-item-externally',
  templateUrl: './add-item-externally.component.html',
  styleUrls: ['./add-item-externally.component.scss'],
})
export class AddItemExternallyComponent implements OnInit {

  collectionList: Collection[];
  collectionId: number;
  searchResults: ItemSearch[];

  constructor(private collectionService: CollectionService,
              private itemService: ItemService) { }

  ngOnInit() {
    this.collectionService.getUserCollections().subscribe(data => {
      this.collectionList = data;
    });
  }

  collectionSelectionChanged(collectionId: number) {

    this.collectionId = collectionId;
  }

  onKey(event) {
    const inputValue = event.target.value;

    if (inputValue.length % 2 === 0) {
      this.itemService.searchItem(inputValue).subscribe(data => this.searchResults = data);
    }
  }

}
