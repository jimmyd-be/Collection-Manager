import {CustomField} from './custom-field';
import {Item} from './item';
import {Directive, OnInit} from '@angular/core';
import {Collection} from './collection';

@Directive()
export class ItemFieldDirective implements OnInit {

  constructor(public item: Item, public field: CustomField[], public collection: Collection) {
  }

  ngOnInit(): void {
  }
}
