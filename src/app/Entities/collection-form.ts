import { FormControl, FormArray } from '@angular/forms';
import { CustomField } from './custom-field';
import { NG_MODEL_WITH_FORM_CONTROL_WARNING } from '@angular/forms/src/directives';

export class CollectionForm {

    name = new FormControl();
    type = new FormControl();

    fields = new FormArray([]);

    constructor(){
      }
}
