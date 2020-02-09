import { Component, OnInit, Input } from '@angular/core';
import { ItemSearch } from '../../Entities/ItemSearch';

@Component({
  selector: 'ngx-search-item-result',
  templateUrl: './search-item-result.component.html',
  styleUrls: ['./search-item-result.component.scss'],
})
export class SearchItemResultComponent implements OnInit {

  @Input() item: ItemSearch;

  constructor() { }

  ngOnInit() {
  }

}
