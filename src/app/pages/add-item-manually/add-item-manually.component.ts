import { Component, OnInit } from '@angular/core';
import { CollectionService } from '../../Services/collection.service';
import { Collection } from '../../Entities/collection';
import { CustomFieldService } from '../../Services/custom-field.service';
import { FormGroup } from '@angular/forms';
import { ManualFormService } from '../../Services/manual-form.service';
import { CustomField } from '../../Entities/custom-field';

@Component({
  selector: 'add-item-manually',
  templateUrl: './add-item-manually.component.html',
  styleUrls: ['./add-item-manually.component.scss']
})
export class AddItemManuallyComponent implements OnInit {

collectionList: Collection[];

  form: FormGroup;
  fields: CustomField[];

  constructor(private collectionService : CollectionService, private customfieldService: CustomFieldService, private formService : ManualFormService) { }

  ngOnInit() {

    this.collectionList = this.collectionService.getUserCollections();
  }


  collectionSelectionChanged(collectionId : Number){

    this.fields = this.customfieldService.getFieldsByCollection(collectionId);
    
    this.form = this.formService.toFormGroup(this.fields);

  }

  onSubmit() {
   // this.payLoad = JSON.stringify(this.form.value);
  }
}
