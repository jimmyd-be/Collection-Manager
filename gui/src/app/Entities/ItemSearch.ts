import { OnInit } from '@angular/core';

export class ItemSearch implements OnInit {

    constructor(public externalId: string, public name: string, public image: string, public reaseDate: string, public source: string) {}

    ngOnInit(): void {
    }
}
