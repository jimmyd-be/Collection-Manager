import { Component, OnInit, Input } from '@angular/core';
import { Item } from '../../Entities/item';
import { CustomField } from '../../Entities/custom-field';

@Component({
  selector: 'ngx-view-item',
  templateUrl: './view-item.component.html',
  styleUrls: ['./view-item.component.scss'],
})
export class ViewItemComponent implements OnInit {

  @Input() item: Item;
  @Input() fields: CustomField[];

  constructor() { }

  ngOnInit() {
  }

  getFieldsByLocation(location: String): CustomField[] {
    return this.fields.filter((field: CustomField) => {
      return field.place === location;
    });
  }

}
