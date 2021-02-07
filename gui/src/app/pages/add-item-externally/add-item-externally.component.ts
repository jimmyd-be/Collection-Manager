import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Collection } from '../../Entities/collection';
import { CollectionService } from '../../Services/collection.service';
import { ItemService } from '../../Services/item.service';
import { ItemSearchDirective } from '../../Entities/ItemSearch';

@Component({
  selector: 'app-add-item-externally',
  templateUrl: './add-item-externally.component.html',
  styleUrls: ['./add-item-externally.component.scss'],
})
export class AddItemExternallyComponent implements OnInit {

  collectionList: Collection[];
  collectionId: number;
  searchResults: ItemSearchDirective[];

  constructor(private collectionService: CollectionService,
              private itemService: ItemService,
              private chRef: ChangeDetectorRef) { }

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

    if (inputValue.length > 2) {

      const selectedCollection = this.collectionList.filter(x => x.id === this.collectionId)[0];

      this.itemService.searchItem(inputValue, selectedCollection.type).subscribe(data => {
        this.searchResults = data;
        this.chRef.detectChanges();
      });
    }
  }

}
