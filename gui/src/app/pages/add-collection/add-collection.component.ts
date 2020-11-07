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
  selector: 'app-add-collection',
  templateUrl: './add-collection.component.html',
  styleUrls: ['./add-collection.component.scss'],
})
export class AddCollectionComponent implements OnInit, OnDestroy {

  private customFieldId = 'id';
  private customFieldName = 'name';
  private customFieldType = 'type';
  private customFieldOptions = 'options';
  private customFieldRequired = 'required';
  private customFieldPlaceholder = 'placeholder';
  private customFieldFieldOrder = 'fieldOrder';
  private customFieldPlace = 'place';
  private customFieldMultiValue = 'multiValue';
  private customFieldLabelPos = 'labelposition';
  private customFieldLabel = 'label';

  addCollectionGroup: FormGroup;

  customFieldFormSub: Subscription;
  collectionTypes: string[];
  customFields: FormArray = new FormArray([]);

  collection: Collection;
  fields: CustomField[];
  editMode = false;

  constructor(private collectionService: CollectionService,
              private formService: AddCollectionService,
              private route: ActivatedRoute,
              private router: Router) { }

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

    this.collectionService.getCollectionTypes().subscribe(type => {
      this.collectionTypes = type;
    });
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
        customField[this.customFieldId],
        customField[this.customFieldName],
        customField[this.customFieldType],
        customField[this.customFieldOptions],
        customField[this.customFieldRequired],
        customField[this.customFieldPlaceholder],
        customField[this.customFieldFieldOrder],
        customField[this.customFieldPlace],
        customField[this.customFieldMultiValue],
        customField[this.customFieldLabelPos],
        customField[this.customFieldLabel], '', '');

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
