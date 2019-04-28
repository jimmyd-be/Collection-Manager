import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, FormArray, Validators } from '@angular/forms'; 
import { CollectionService } from '../../Services/collection.service';
import { Subscription } from 'rxjs';
import { AddCollectionService } from '../../Services/add-collection.service';
import { Collection } from '../../Entities/collection';
import { CustomField } from '../../Entities/custom-field';
import { ParamMap, ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { CustomFieldService } from '../../Services/custom-field.service';

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

  collection: Collection;
  fields: CustomField[];
  editMode: boolean = false;

  constructor(private collectionService: CollectionService, private formBuilder: FormBuilder, private formService: AddCollectionService, private fb: FormBuilder, private route: ActivatedRoute, private customFieldService: CustomFieldService) { }

  ngOnInit() {

    this.route.paramMap.subscribe((params: ParamMap) => {
      if(params.has('id'))
      {
        this.editMode= true;
        this.collection = this.collectionService.getUserCollection(+params.get('id'));
        this.fields = this.customFieldService.getFieldsByCollection(+params.get('id'));

        for(let field of this.fields)
        {
          this.formService.addCustomFieldByField(field)

        }
      }
    });
    
    this.customFieldFormSub = this.formService.collectionForm
    .subscribe(group => {
        this.addCollectionGroup = group
        this.customFields = this.addCollectionGroup.get('fields') as FormArray

        if(this.editMode)
        {
          this.addCollectionGroup.get('name').setValue(this.collection.name);
          this.addCollectionGroup.get('type').setValue(this.collection.type);
          this.addCollectionGroup.get('type').disable();
        }      
      });

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

    if(!this.editMode)
    {
        let collection = new Collection(-1, this.addCollectionGroup.value.name, this.addCollectionGroup.value.type, [], fields);

        this.collectionService.createCollection(collection);
    }
    else
    {
      this.collection.name = this.addCollectionGroup.value.name;
      this.collection.fields = fields;

      this.collectionService.editCollection( this.collection);
    }
  }

}
