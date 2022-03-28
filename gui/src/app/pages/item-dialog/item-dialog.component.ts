import {Component} from '@angular/core';
import {Item} from '../../Entities/item';
import {CustomField} from '../../Entities/custom-field';
import {Collection} from '../../Entities/collection';
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";

@Component({
  selector: 'app-item-dialog',
  templateUrl: './item-dialog.component.html',
  styleUrls: ['./item-dialog.component.scss'],
})
export class ItemDialogComponent {

  item: Item;
  field: CustomField[];
  collection: Collection;

  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig) {
  }


  deleteItem() {
    this.ref.close('delete');
  }

  editItem() {
    this.ref.close('edit');
  }

}
