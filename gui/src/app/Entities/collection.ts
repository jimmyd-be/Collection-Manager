import { CustomField } from './custom-field';

export class Collection {

    constructor(public id: number,
        public name: string,
        public type: string,
        public members: string[] = [],
        public fields: CustomField[] = [],
    ) {}
}
