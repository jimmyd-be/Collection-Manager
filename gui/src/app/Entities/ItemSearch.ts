import { CustomField } from './custom-field';
import { Item } from './item';
import { OnInit } from '@angular/core';
import { Collection } from './collection';

export class ItemSearch implements OnInit {

    constructor(public externalId: string, public name: string, public image: string, public reaseDate: string, public source: string) {}

    ngOnInit(): void {
    }
}
