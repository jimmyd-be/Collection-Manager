import { Component, OnInit, Input } from '@angular/core';
import { Item } from '../../Entities/item';

@Component({
  selector: 'ngx-view-item',
  templateUrl: './view-item.component.html',
  styleUrls: ['./view-item.component.scss'],
})
export class ViewItemComponent implements OnInit {

  @Input() item: Item;

  constructor() { }

  ngOnInit() {
  }

}
