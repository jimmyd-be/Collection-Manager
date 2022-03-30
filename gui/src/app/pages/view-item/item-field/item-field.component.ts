import {Component, Input, OnInit} from '@angular/core';
import {Item} from '../../../Entities/item';
import {CustomField} from '../../../Entities/custom-field';
import {ItemData} from '../../../Entities/ItemData';

@Component({
  selector: 'app-item-field',
  templateUrl: './item-field.component.html',
  styleUrls: ['./item-field.component.scss'],
})
export class ItemFieldComponent implements OnInit{

  @Input() item: Item;
  @Input() field: CustomField;

  rating: number;

  constructor() {
  }

  ngOnInit() {
    if(this.getItemValue().length > 0) {
      this.rating = parseInt(this.getItemValue()[0]);
    } else {
      this.rating = 0;
    }
  }

  getItemValue(): string[] {

    if (this.field.name === 'cover') {
      return [this.item.image];
    } else {
      return this.item.data.filter((data: ItemData) => {
        return this.field.id === data.fieldId;
      }).map((item: ItemData) => {
        return item.value;
      });
    }
  }
}
