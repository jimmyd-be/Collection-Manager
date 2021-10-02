export class CustomField {

  constructor(
    public id: number,
    public name: string,
    public type: string,
    public options: string[],
    public required: boolean,
    public placeholder: string,
    public fieldOrder: number,
    public place: string,
    public multivalues: boolean,
    public labelPosition: string,
    public label: string,
    public widget: string,
    public value: string,
    public valueNumber: number = 0,
    public formId: string = '') {
  }
}
