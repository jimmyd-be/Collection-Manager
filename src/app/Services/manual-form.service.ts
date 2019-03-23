import { Injectable } from '@angular/core';
import { CustomField } from '../Entities/custom-field';
import { FormGroup, Validators, FormControl } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ManualFormService {

  constructor() { }


  toFormGroup(fields: CustomField[] ) {
    let group: any = {};

    fields.forEach(field => {

      if(field.type == 'url')
      {
        group[field.name + '_label'] = field.required ? new FormControl(field.value || '', Validators.required)
                                              : new FormControl(field.value || '');
      }

      group[field.name] = field.required ? new FormControl(field.value || '', Validators.required)
                                              : new FormControl(field.value || '');
    });
    return new FormGroup(group);
  }
}
