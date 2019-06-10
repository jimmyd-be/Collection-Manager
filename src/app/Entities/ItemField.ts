import { CustomField } from './custom-field';
import { Item } from './item';
import { OnInit } from '@angular/core';

export class ItemField implements OnInit {

    constructor(public item: Item, public field: CustomField[]) {}

    ngOnInit(): void {
    }
}
