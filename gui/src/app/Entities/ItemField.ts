import {CustomField} from './custom-field';
import {Item} from './item';
import {Directive} from '@angular/core';
import {Collection} from './collection';

@Directive()
export class ItemFieldDirective {

  constructor(public item: Item, public field: CustomField[], public collection: Collection) {
  }

}
