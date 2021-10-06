import {Component, Input} from '@angular/core';
import {CustomField} from '../../../Entities/custom-field';
import {FormGroup} from '@angular/forms';
import {faMinusCircle, faPlusCircle} from '@fortawesome/free-solid-svg-icons';
import {AddItemManuallyComponent} from '../add-item-manually.component';

@Component({
  selector: 'app-custom-field-form',
  templateUrl: './custom-field-form.component.html',
  styleUrls: ['./custom-field-form.component.scss'],
})
export class CustomFieldFormComponent {
  @Input() field: CustomField;
  @Input() form: FormGroup;
  @Input() parentComponent: AddItemManuallyComponent;

  addIcon = faPlusCircle;
  removeIcon = faMinusCircle;


  constructor() {
  }

  get isValid() {
    if (this.field.required) {
      return this.form.controls[this.field.name].valid;
    } else {
      return true;
    }
  }

  addField() {
    this.parentComponent.addField(this.field);
  }

  deleteField() {
    this.parentComponent.deleteField(this.field);
  }
}
