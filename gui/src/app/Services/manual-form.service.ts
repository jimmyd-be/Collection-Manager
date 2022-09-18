import { Injectable } from '@angular/core';
import { CustomField } from '../Entities/custom-field';
import { UntypedFormGroup, Validators, UntypedFormControl } from '@angular/forms';
import { Item } from '../Entities/item';
import { CustomFieldService } from './custom-field.service';

@Injectable({
  providedIn: 'root',
})
export class ManualFormService {

  fields: CustomField[];

  constructor(private customfieldService: CustomFieldService) { }

  addFieldToList(field: CustomField, numberValue: number) {

    let value = field.valueNumber + 1;
    if (numberValue) {
      value = numberValue;
    }

    const newField = new CustomField(field.id, field.name, field.type, field.options,
      field.required, field.placeholder, field.fieldOrder, field.place, field.multivalues,
      field.labelPosition, field.label, '', '', value, '');

    this.fields.push(newField);

    this.fields = this.customfieldService.sortFields(this.fields);

    return newField;
  }

  addField(field: CustomField, form: UntypedFormGroup) {

    const newField = this.addFieldToList(field, null);

    this.addFieldToForm(newField, form);
  }

  toFormGroup(item: Item ) {
    const group: any = {};

    this.fields.forEach(field => {

      let currentField = field;

      if (currentField.valueNumber === 0) {

      const value: string[] = this.getValueOfField(currentField, item);

      for (let i = 0; i < value.length; i++) {

        if (i > 0) {
          currentField = this.addFieldToList(field, i);
        }

        currentField.formId = currentField.id + '_' + currentField.valueNumber;

        if (currentField.type === 'url') {
          group[currentField.formId + '_label'] = currentField.required ? new UntypedFormControl(value[i] || '', Validators.required)
                                                : new UntypedFormControl(value[i] || '');
        }

        group[currentField.formId] = currentField.required ? new UntypedFormControl(value[i] || '', Validators.required)
                                                : new UntypedFormControl(value[i] || '');
      }
    }
    });
    return new UntypedFormGroup(group);
  }
  getValueOfField(field: CustomField, item: Item): string[] {
    let value: Array<string> = new Array();

    if (item) {
      if (field.name === 'title') {
        value.push(item.name);
      } else if (field.name === 'cover') {
        value.push(item.image);
      } else {
        value = item.data.filter(data => data.fieldId === field.id).map(data => data.value);
      }
    }

    if (value.length === 0) {
      value.push('');
    }

    return value;
  }

  addFieldToForm(field: CustomField, form: UntypedFormGroup) {
    field.formId = field.id + '_' + field.valueNumber;

    form.addControl(field.formId, field.required ? new UntypedFormControl(field.value || '', Validators.required)
    : new UntypedFormControl(field.value || ''));
  }

  deleteFieldToForm(field: CustomField, form: UntypedFormGroup) {
    form.removeControl(field.formId);
  }

  deleteField(field: CustomField, form: UntypedFormGroup) {
    const index =  this.fields.indexOf(field, 0);
    if (index > -1) {
      this.fields.splice(index, 1);
    }

    this.deleteFieldToForm(field, form);
  }
}
