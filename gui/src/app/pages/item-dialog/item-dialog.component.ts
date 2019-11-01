import { Component, OnInit, Input } from '@angular/core';
import { Item } from '../../Entities/item';
import { CustomField } from '../../Entities/custom-field';
import { faTrash, faEdit } from '@fortawesome/free-solid-svg-icons';
import { ItemService } from '../../Services/item.service';
import { Collection } from '../../Entities/collection';
import { NbDialogRef } from '@nebular/theme';

@Component({
  selector: 'ngx-item-dialog',
  templateUrl: './item-dialog.component.html',
  styleUrls: ['./item-dialog.component.scss'],
})
export class ItemDialogComponent implements OnInit {

  deleteIcon = faTrash;
  editIcon = faEdit;

  item: Item;
  field: CustomField[];
  collection: Collection;

  constructor(private itemService: ItemService, private dialogRef: NbDialogRef<ItemDialogComponent>) { }

  ngOnInit() {}

  deleteItem() {
    this.dialogRef.close('delete');
  }

  editItem() {
    this.dialogRef.close('edit');
  }

}
