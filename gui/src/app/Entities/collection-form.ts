import {FormControl, FormArray} from '@angular/forms';

export class CollectionForm {

  id = new FormControl();
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

  constructor() {
  }
}
