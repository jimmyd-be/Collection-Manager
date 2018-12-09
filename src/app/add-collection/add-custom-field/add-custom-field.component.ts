import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-add-custom-field',
  templateUrl: './add-custom-field.component.html',
  styleUrls: ['./add-custom-field.component.css']
})
export class AddCustomFieldComponent implements OnInit {

  @Input() customFieldForm: FormGroup
  @Input() index: number
  @Output() deleteCustomField: EventEmitter<number> = new EventEmitter()

  constructor() { }

  ngOnInit() {
   // this.deleteCustomField.emit(this.index)
  }

  delete() {
    this.deleteCustomField.emit(this.index)
  }

}
