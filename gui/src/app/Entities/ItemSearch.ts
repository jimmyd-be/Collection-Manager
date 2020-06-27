import { OnInit, Directive } from '@angular/core';

@Directive()
export class ItemSearch implements OnInit {

    constructor(
        public externalId: string,
        public name: string,
        public image: string,
        public reaseDate: string,
        public source: string,
        public url: string) {}

    ngOnInit(): void {
    }
}
