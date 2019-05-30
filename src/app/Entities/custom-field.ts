export class CustomField {

    constructor(public name: string,
        public type: string,
        public options: string,
        public required: boolean,
        public placeholder: string,
        public fieldOrder: number,
        public place: string,
        public multivalues: boolean,
        public labelPosition: string,
        public label: string,
        public value: string) {}
}
