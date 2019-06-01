import { Injectable } from '@angular/core';
import { CustomField } from '../Entities/custom-field';
import { FormGroup, Validators, FormControl } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class ManualFormService {
  constructor() { }


  toFormGroup(fields: CustomField[] ) {
    const group: any = {};

    fields.forEach(field => {

      field.formId = field.id + '_' + field.valueNumber;

      if (field.type === 'url') {
        group[field.formId + '_label'] = field.required ? new FormControl(field.value || '', Validators.required)
                                              : new FormControl(field.value || '');
      }

      group[field.formId] = field.required ? new FormControl(field.value || '', Validators.required)
                                              : new FormControl(field.value || '');
    });
    return new FormGroup(group);
  }

  addFieldToForm(field: CustomField, form: FormGroup) {
    field.formId = field.id + '_' + field.valueNumber;

    form.addControl(field.formId, field.required ? new FormControl(field.value || '', Validators.required)
    : new FormControl(field.value || ''));
  }

  deleteFieldToForm(field: CustomField, form: FormGroup) {
    form.removeControl(field.formId);
  }
}
