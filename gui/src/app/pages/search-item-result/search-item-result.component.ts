import { Component, OnInit, Input } from '@angular/core';
import { ItemSearchDirective } from '../../Entities/ItemSearch';
import { ItemService } from '../../Services/item.service';
import {NbToastrService} from '@nebular/theme';

@Component({
  selector: 'app-search-item-result',
  templateUrl: './search-item-result.component.html',
  styleUrls: ['./search-item-result.component.scss'],
})
export class SearchItemResultComponent implements OnInit {

  @Input() item: ItemSearchDirective;
  @Input() collectionId: number;
  disableActions = false;

  constructor(private itemService: ItemService, private toastrService: NbToastrService) { }

  ngOnInit() {
  }

  addItemToCollection(source: string, externalId: string, name: string) {

    this.disableActions = true;
    this.itemService.addExternalItemToCollection(this.collectionId, source, externalId).subscribe(o => {
      this.toastrService.success(name + ' has been added to the collection');
    },
    error => {
      this.toastrService.danger('Something went wrong when adding ' + name + ' the collection');
      this.disableActions = false;
    });

  }

}
