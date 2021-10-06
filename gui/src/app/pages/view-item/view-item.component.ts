import {Component, Input} from '@angular/core';
import {Item} from '../../Entities/item';
import {CustomField} from '../../Entities/custom-field';

@Component({
  selector: 'app-view-item',
  templateUrl: './view-item.component.html',
  styleUrls: ['./view-item.component.scss'],
})
export class ViewItemComponent {

  @Input() item: Item;
  @Input() fields: CustomField[];

  constructor() {
  }

  getFieldsByLocation(location: string): CustomField[] {
    return this.fields.filter((field: CustomField) => {
      return field.place === location;
    });
  }

}
