import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, FormArray, Validators } from '@angular/forms'; 
import { CollectionService } from '../Services/collection.service';
import { CustomField } from '../Entities/custom-field';
import { Subscription } from 'rxjs';
import { AddCollectionService } from '../Services/add-collection.service';
import { CollectionForm } from '../Entities/collection-form';

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

    console.log(this.addCollectionGroup.value.name);
    console.log(this.addCollectionGroup.value.fields);
  }

}
