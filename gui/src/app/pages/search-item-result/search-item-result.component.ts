import {Component, Input} from '@angular/core';
import {ItemSearchDirective} from '../../Entities/ItemSearch';
import {ItemService} from '../../Services/item.service';
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-search-item-result',
  templateUrl: './search-item-result.component.html',
  styleUrls: ['./search-item-result.component.scss'],
})
export class SearchItemResultComponent {

  @Input() item: ItemSearchDirective;
  @Input() collectionId: number;
  disableActions = false;

  constructor(private itemService: ItemService,
              private messageService: MessageService) {
  }

  addItemToCollection(source: string, externalId: string, name: string) {

    this.disableActions = true;
    this.itemService.addExternalItemToCollection(this.collectionId, source, externalId).subscribe(o => {
        this.messageService.add({severity:'success', summary: name + ' has been added to the collection'});

      },
      error => {
        this.messageService.add({severity:'error', summary: 'Something went wrong when adding ' + name + ' the collection'});
        this.disableActions = false;
      });

  }

}
