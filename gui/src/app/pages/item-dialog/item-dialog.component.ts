import {Component, OnInit} from '@angular/core';
import {Item} from '../../Entities/item';
import {CustomField} from '../../Entities/custom-field';
import {Collection} from '../../Entities/collection';
import {NbDialogRef} from '@nebular/theme';

@Component({
  selector: 'app-item-dialog',
  templateUrl: './item-dialog.component.html',
  styleUrls: ['./item-dialog.component.scss'],
})
export class ItemDialogComponent implements OnInit {

  item: Item;
  field: CustomField[];
  collection: Collection;

  constructor(private dialogRef: NbDialogRef<ItemDialogComponent>) {
  }

  ngOnInit() {
  }

  deleteItem() {
    this.dialogRef.close('delete');
  }

  editItem() {
    this.dialogRef.close('edit');
  }

}
