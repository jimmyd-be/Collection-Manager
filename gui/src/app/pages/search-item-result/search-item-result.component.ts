import { Component, OnInit, Input } from '@angular/core';
import { ItemSearchDirective } from '../../Entities/ItemSearch';
import { ItemService } from '../../Services/item.service';

@Component({
  selector: 'app-search-item-result',
  templateUrl: './search-item-result.component.html',
  styleUrls: ['./search-item-result.component.scss'],
})
export class SearchItemResultComponent implements OnInit {

  @Input() item: ItemSearchDirective;
  @Input() collectionId: number;

  constructor(private itemService: ItemService) { }

  ngOnInit() {
  }

  addItemToCollection(source: string, externalId: string) {
    this.itemService.addExternalItemToCollection(this.collectionId, source, externalId).subscribe();
  }

}
