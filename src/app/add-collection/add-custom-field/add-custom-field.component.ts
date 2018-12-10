import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import { CustomFieldService } from 'src/app/Services/custom-field.service';

@Component({
  selector: 'app-add-custom-field',
  templateUrl: './add-custom-field.component.html',
  styleUrls: ['./add-custom-field.component.css']
})
export class AddCustomFieldComponent implements OnInit {

  @Input() customFieldForm: FormGroup
  @Input() index: number
  @Output() deleteCustomField: EventEmitter<number> = new EventEmitter()
  removeIcon = faTrash;

  types: string[];
  labelPositions: string[];
  fieldPositions: string[];
  

  constructor(private fieldService: CustomFieldService) { }

  ngOnInit() {

    this.types = this.fieldService.getCustomFieldTypes();
    this.labelPositions = this.fieldService.getLabelPositions();
    this.fieldPositions = this.fieldService.getFieldPositions();

  }

  delete() {
    this.deleteCustomField.emit(this.index)
  }

}
