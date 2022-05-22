import {Component, OnInit} from '@angular/core';
import {Item} from '../../Entities/item';
import {CustomField} from '../../Entities/custom-field';
import {Collection} from '../../Entities/collection';
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {faEdit, faTrash} from "@fortawesome/free-solid-svg-icons";
import {ItemFieldDirective} from "../../Entities/ItemField";

@Component({
  selector: 'app-item-dialog',
  templateUrl: './item-dialog.component.html',
  styleUrls: ['./item-dialog.component.scss'],
})
export class ItemDialogComponent implements OnInit {

  removeIcon = faTrash;
  editIcon = faEdit;
  itemFieldDirective: ItemFieldDirective;

  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig) {
  }

  ngOnInit() {
    this.itemFieldDirective = this.config.data.itemFieldDirective;
  }


  deleteItem() {
    this.ref.close('delete');
  }

  editItem() {
   this.ref.close('edit');
  }

}
