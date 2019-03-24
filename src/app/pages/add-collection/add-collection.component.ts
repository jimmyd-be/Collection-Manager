import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, FormArray, Validators } from '@angular/forms'; 
import { CollectionService } from '../../Services/collection.service';
import { Subscription } from 'rxjs';
import { AddCollectionService } from '../../Services/add-collection.service';
import { Collection } from '../../Entities/collection';
import { CustomField } from '../../Entities/custom-field';

@Component({
  selector: 'app-add-collection',
  templateUrl: './add-collection.component.html',
  styleUrls: ['./add-collection.component.css']
})
export class AddCollectionComponent implements OnInit {

  addCollectionGroup: FormGroup;

  customFieldFormSub: Subscription;
  collectionTypes: string[];
  customFields: FormArray = new FormArray([]);

  constructor(private collectionService: CollectionService, private formBuilder: FormBuilder, private formService: AddCollectionService, private fb: FormBuilder) { }

  ngOnInit() {

    this.addCollectionGroup = new FormGroup({
      name: new FormControl(''),
      type: new FormControl(''),
      fields: new FormArray([])
    });

    this.customFieldFormSub = this.formService.teamForm$
    .subscribe(team => {
        this.addCollectionGroup = team
        this.customFields = this.addCollectionGroup.get('fields') as FormArray
      })


    this.collectionTypes = this.collectionService.getCollectionTypes();
  }

  ngOnDestroy() {
    this.customFieldFormSub.unsubscribe()
  }

  addCustomField() {

    this.formService.addCustomField()
  }

  deletePlayer(index: number) {
    this.formService.deleteCustomField(index)
  }

  onSubmit(){

    let fields = [];

    for(let customField of this.addCollectionGroup.value.fields)
    {
      let newField = new CustomField(
      customField['name'],
      customField['type'],
      customField['options'],
      customField['required'],
      customField['placeholder'],
      customField['fieldOrder'],
      customField['place'],
      customField['multiValue'],      
      customField['labelposition'],
      customField['label'], "");

      fields.push(newField);
    }


    let collection = new Collection(-1, this.addCollectionGroup.value.name, this.addCollectionGroup.value.type, [], fields);

    this.collectionService.createCollection(collection);
  }

}
