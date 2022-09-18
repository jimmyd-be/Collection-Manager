import {UntypedFormControl, UntypedFormArray} from '@angular/forms';

export class CollectionForm {

  id = new UntypedFormControl();
  name = new UntypedFormControl();
  type = new UntypedFormControl();
  choises = new UntypedFormControl();
  label = new UntypedFormControl();
  placeholder = new UntypedFormControl();
  required = new UntypedFormControl();
  multiValue = new UntypedFormControl();

  fieldOrder = new UntypedFormControl();
  place = new UntypedFormControl();
  labelposition = new UntypedFormControl();
  widget = new UntypedFormControl();

  fields = new UntypedFormArray([]);

  constructor() {
  }
}
