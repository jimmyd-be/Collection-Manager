import { CustomField } from './custom-field';
import { Item } from './item';
import { OnInit } from '@angular/core';
import { Collection } from './collection';

export class ItemField implements OnInit {

    constructor(public item: Item, public field: CustomField[], public collection: Collection) {}

    ngOnInit(): void {
    }
}
