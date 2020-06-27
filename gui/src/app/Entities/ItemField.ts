import { CustomField } from './custom-field';
import { Item } from './item';
import { OnInit, Directive } from '@angular/core';
import { Collection } from './collection';

@Directive()
export class ItemField implements OnInit {

    constructor(public item: Item, public field: CustomField[], public collection: Collection) {}

    ngOnInit(): void {
    }
}
