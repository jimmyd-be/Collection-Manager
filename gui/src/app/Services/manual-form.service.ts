import { Injectable } from '@angular/core';
import { CustomField } from '../Entities/custom-field';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { Item } from '../Entities/item';
import { isDefined } from '@angular/compiler/src/util';
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

  addField(field: CustomField, form: FormGroup) {

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
          group[currentField.formId + '_label'] = currentField.required ? new FormControl(value[i] || '', Validators.required)
                                                : new FormControl(value[i] || '');
        }

        group[currentField.formId] = currentField.required ? new FormControl(value[i] || '', Validators.required)
                                                : new FormControl(value[i] || '');
      }
    }
    });
    return new FormGroup(group);
  }
  getValueOfField(field: CustomField, item: Item): string[] {
    let value: Array<string> = new Array();

    if (isDefined(item)) {
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

  addFieldToForm(field: CustomField, form: FormGroup) {
    field.formId = field.id + '_' + field.valueNumber;

    form.addControl(field.formId, field.required ? new FormControl(field.value || '', Validators.required)
    : new FormControl(field.value || ''));
  }

  deleteFieldToForm(field: CustomField, form: FormGroup) {
    form.removeControl(field.formId);
  }

  deleteField(field: CustomField, form: FormGroup) {
    const index =  this.fields.indexOf(field, 0);
    if (index > -1) {
      this.fields.splice(index, 1);
    }

    this.deleteFieldToForm(field, form);
  }
}
