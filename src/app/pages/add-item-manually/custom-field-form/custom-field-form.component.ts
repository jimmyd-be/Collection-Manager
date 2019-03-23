import { Component, OnInit, Input } from '@angular/core';
import { CustomField } from '../../../Entities/custom-field';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'custom-field-form',
  templateUrl: './custom-field-form.component.html',
  styleUrls: ['./custom-field-form.component.scss']
})
export class CustomFieldFormComponent implements OnInit {
  @Input() field: CustomField;
  @Input() form: FormGroup;
  

  constructor() { }

  ngOnInit() {
  }

  get isValid() { 
  
    if(this.field.required)
    {
      return this.form.controls[this.field.name].valid; 
    }
    else{
      return true;
    }
  }
}
