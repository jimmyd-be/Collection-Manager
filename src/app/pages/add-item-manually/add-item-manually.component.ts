import { Component, OnInit } from '@angular/core';
import { CollectionService } from '../../Services/collection.service';
import { Collection } from '../../Entities/collection';
import { CustomFieldService } from '../../Services/custom-field.service';
import { FormGroup } from '@angular/forms';
import { ManualFormService } from '../../Services/manual-form.service';
import { CustomField } from '../../Entities/custom-field';
import { ItemService } from '../../Services/item.service';

@Component({
  selector: 'ngx-add-item-manually',
  templateUrl: './add-item-manually.component.html',
  styleUrls: ['./add-item-manually.component.scss'],
})
export class AddItemManuallyComponent implements OnInit {

  collectionList: Collection[];
  collectionId: Number;
  form: FormGroup;
  fields: CustomField[];

  constructor(private collectionService: CollectionService,
    private customfieldService: CustomFieldService,
    private formService: ManualFormService,
    private itemService: ItemService) { }

  ngOnInit() {
    this.collectionService.getUserCollections().subscribe(data => {

      this.collectionList = data;
    });
  }


  collectionSelectionChanged(collectionId: Number) {

    this.collectionId = collectionId;

    this.customfieldService.getFieldsByCollection(collectionId).subscribe(data => {
      this.fields = data;
      this.form = this.formService.toFormGroup(this.fields);
    });

  }

  onSubmit() {
    this.itemService.addItemToCollection(this.collectionId, JSON.stringify(this.form.value));

  }
}
