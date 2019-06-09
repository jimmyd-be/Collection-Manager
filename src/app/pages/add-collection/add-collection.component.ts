import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormArray } from '@angular/forms';
import { CollectionService } from '../../Services/collection.service';
import { Subscription } from 'rxjs';
import { AddCollectionService } from '../../Services/add-collection.service';
import { Collection } from '../../Entities/collection';
import { CustomField } from '../../Entities/custom-field';
import { ParamMap, ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'ngx-app-add-collection',
  templateUrl: './add-collection.component.html',
  styleUrls: ['./add-collection.component.css'],
})
export class AddCollectionComponent implements OnInit, OnDestroy {

  addCollectionGroup: FormGroup;

  customFieldFormSub: Subscription;
  collectionTypes: string[];
  customFields: FormArray = new FormArray([]);

  collection: Collection;
  fields: CustomField[];
  editMode: boolean = false;

  constructor(private collectionService: CollectionService,
    private formService: AddCollectionService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {

    this.route.paramMap.subscribe((params: ParamMap) => {
      if (params.has('id')) {
        this.editMode = true;
        this.collectionService.getUserCollection(+params.get('id')).subscribe(data => {
          this.collection = data;
          this.fields = data.fields;

          this.addCollectionGroup.get('name').setValue(this.collection.name);
          this.addCollectionGroup.get('type').setValue(this.collection.type);
          this.addCollectionGroup.get('type').disable();

          for (const field of this.fields) {
            this.formService.addCustomFieldByField(field);
          }
        });
      }

      this.customFieldFormSub = this.formService.collectionForm
        .subscribe(group => {
          this.addCollectionGroup = group;
          this.customFields = this.addCollectionGroup.get('fields') as FormArray;
        });
    });

    this.collectionTypes = this.collectionService.getCollectionTypes();
  }

  ngOnDestroy() {
    this.customFieldFormSub.unsubscribe();
  }

  addCustomField() {

    this.formService.addCustomField();
  }

  deletePlayer(index: number) {
    this.formService.deleteCustomField(index);
  }

  onSubmit() {

    const fields = [];

    for (const customField of this.addCollectionGroup.value.fields) {
      const newField = new CustomField(
        customField['id'],
        customField['name'],
        customField['type'],
        customField['options'],
        customField['required'],
        customField['placeholder'],
        customField['fieldOrder'],
        customField['place'],
        customField['multiValue'],
        customField['labelposition'],
        customField['label'], '', '');

      fields.push(newField);
    }

    if (!this.editMode) {
      const collection = new Collection(-1, this.addCollectionGroup.value.name, this.addCollectionGroup.value.type, [], fields);

      this.collectionService.createCollection(collection).subscribe(data => {
        this.router.navigate(['/pages/dashboard']);
      });
    } else {
      this.collection.name = this.addCollectionGroup.value.name;
      this.collection.fields = fields;

      this.collectionService.editCollection(this.collection).subscribe(data => {
        this.router.navigate(['/pages/dashboard']);
      });
    }
  }

}
