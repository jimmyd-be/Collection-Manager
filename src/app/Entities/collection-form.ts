import { FormControl, FormArray } from '@angular/forms';
import { CustomField } from './custom-field';
import { NG_MODEL_WITH_FORM_CONTROL_WARNING } from '@angular/forms/src/directives';

export class CollectionForm {

    name = new FormControl();
    type = new FormControl();
    choises = new FormControl();
    label = new FormControl();
    placeholder = new FormControl();
    required = new FormControl();
    multiValue = new FormControl();

    fieldOrder = new FormControl();
    place = new FormControl();
    labelposition = new FormControl();
    widget = new FormControl();

    fields = new FormArray([]);

    constructor(){
      }
}
