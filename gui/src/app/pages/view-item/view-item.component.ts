import {Component, Input, OnInit} from '@angular/core';
import {Item} from '../../Entities/item';
import {CustomField} from '../../Entities/custom-field';

@Component({
  selector: 'app-view-item',
  templateUrl: './view-item.component.html',
  styleUrls: ['./view-item.component.scss'],
})
export class ViewItemComponent implements OnInit {

  @Input() item: Item;
  @Input() fields: CustomField[];

  constructor() {
  }

  ngOnInit() {
  }

  getFieldsByLocation(location: string): CustomField[] {
    return this.fields.filter((field: CustomField) => {
      return field.place === location;
    });
  }

}
