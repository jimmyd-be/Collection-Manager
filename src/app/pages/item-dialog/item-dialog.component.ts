import { Component, OnInit, Input } from '@angular/core';
import { Item } from '../../Entities/item';
import { CustomField } from '../../Entities/custom-field';

@Component({
  selector: 'ngx-item-dialog',
  templateUrl: './item-dialog.component.html',
  styleUrls: ['./item-dialog.component.scss'],
})
export class ItemDialogComponent implements OnInit {

  item: Item;
  field: CustomField[];

  constructor() { }

  ngOnInit() {}

}
