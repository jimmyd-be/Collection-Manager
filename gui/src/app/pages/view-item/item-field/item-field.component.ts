import { Component, OnInit, Input } from '@angular/core';
import { Item } from '../../../Entities/item';
import { CustomField } from '../../../Entities/custom-field';
import { ItemData } from '../../../Entities/ItemData';

@Component({
  selector: 'ngx-item-field',
  templateUrl: './item-field.component.html',
  styleUrls: ['./item-field.component.scss'],
})
export class ItemFieldComponent implements OnInit {

@Input() item: Item;
@Input() field: CustomField;

  constructor() { }

  ngOnInit() {
  }

  getItemValue(): string[] {
    return this.item.data.filter((data: ItemData) => {
      return this.field.id === data.fieldId;
    }).map((item: ItemData) => {
      return item.value;
    });
  }
}
