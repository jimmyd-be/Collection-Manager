import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import { CustomFieldService } from '../../../Services/custom-field.service';


@Component({
  selector: 'ngx-app-add-custom-field',
  templateUrl: './add-custom-field.component.html',
  styleUrls: ['./add-custom-field.component.css'],
})
export class AddCustomFieldComponent implements OnInit {

  @Input() customFieldForm: FormGroup;
  @Input() index: number;
  @Output() deleteCustomField: EventEmitter<number> = new EventEmitter();
  removeIcon = faTrash;
  fieldType: string;

  types: string[];
  labelPositions: string[];
  fieldPositions: string[];

  showValueField: boolean = false;
  showPlaceholderField: boolean = false;
  showRequiredField: boolean = false;
  showAllowMultiField: boolean = false;

  constructor(private fieldService: CustomFieldService) { }

  ngOnInit() {

    this.types = this.fieldService.getCustomFieldTypes();
    this.labelPositions = this.fieldService.getLabelPositions();
    this.fieldPositions = this.fieldService.getFieldPositions();

    this.typeChanged(this.customFieldForm.get('type').value);

  }

  delete() {
    this.deleteCustomField.emit(this.index);
  }

  typeChanged(type: string): void {
    this.fieldType = type;

    switch (type) {
      case 'text':
        this.showValueField = false;
        this.showPlaceholderField = true;
        this.showRequiredField = true;
        this.showAllowMultiField = true;
        break;

      case 'url':
        this.showValueField = false;
        this.showPlaceholderField = false;
        this.showRequiredField = true;
        this.showAllowMultiField = true;
        break;

      case 'textarea':
        this.showValueField = false;
        this.showPlaceholderField = true;
        this.showRequiredField = true;
        this.showAllowMultiField = true;
        break;

      case 'date':
        this.showValueField = false;
        this.showPlaceholderField = true;
        this.showRequiredField = true;
        this.showAllowMultiField = true;
        break;

      case 'dropdown':
        this.showValueField = true;
        this.showPlaceholderField = true;
        this.showRequiredField = true;
        this.showAllowMultiField = true;
        break;

      case 'checkBox':
        this.showValueField = false;
        this.showPlaceholderField = false;
        this.showRequiredField = false;
        this.showAllowMultiField = false;
        break;

      case 'radio':
        this.showValueField = true;
        this.showPlaceholderField = false;
        this.showRequiredField = true;
        this.showAllowMultiField = false;
        break;

      case 'email':
        this.showValueField = false;
        this.showPlaceholderField = false;
        this.showRequiredField = true;
        this.showAllowMultiField = true;
        break;

      case 'rate':
        this.showValueField = false;
        this.showPlaceholderField = true;
        this.showRequiredField = true;
        this.showAllowMultiField = true;
        break;

      case 'image':
        this.showValueField = false;
        this.showPlaceholderField = true;
        this.showRequiredField = true;
        this.showAllowMultiField = true;
        break;
    }
  }

}
