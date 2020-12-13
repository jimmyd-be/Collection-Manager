import { Component, OnInit } from '@angular/core';
import { CollectionService } from '../../Services/collection.service';
import { Collection } from '../../Entities/collection';
import { CustomFieldService } from '../../Services/custom-field.service';
import { FormGroup } from '@angular/forms';
import { ManualFormService } from '../../Services/manual-form.service';
import { CustomField } from '../../Entities/custom-field';
import { ItemService } from '../../Services/item.service';
import { NbToastrService } from '@nebular/theme';
import { ActivatedRoute } from '@angular/router';
import { Item } from '../../Entities/item';

@Component({
  selector: 'app-add-item-manually',
  templateUrl: './add-item-manually.component.html',
  styleUrls: ['./add-item-manually.component.scss'],
})
export class AddItemManuallyComponent implements OnInit {

  collectionList: Collection[];
  collectionId: number;
  form: FormGroup;
  item: Item;

  constructor(private collectionService: CollectionService,
              private customfieldService: CustomFieldService,
              public formService: ManualFormService,
              private itemService: ItemService,
              private toastrService: NbToastrService,
              private route: ActivatedRoute) { }

  ngOnInit() {

    const itemId = Number(this.route.snapshot.paramMap.get('id'));
    this.collectionId = Number(this.route.snapshot.paramMap.get('colId'));

    this.itemService.getItemById(itemId).subscribe(data => this.item = data);

    this.collectionService.getUserCollections().subscribe(data => {
      this.collectionList = data;

      if (this.collectionId !== 0) {
        this.collectionSelectionChanged(this.collectionId);
      }

    });
  }

  collectionSelectionChanged(collectionId: number) {

    this.collectionId = collectionId;

    this.customfieldService.getFieldsByCollection(collectionId).subscribe(data => {
      this.formService.fields = this.customfieldService.sortFields(data);

      this.formService.fields.forEach(field => field.valueNumber = 0);

      this.form = this.formService.toFormGroup(this.item);
    });
  }

  onSubmit() {

    if (this.item) {
      this.itemService.editItemToCollection(this.item.id, this.collectionId, this.form.value).subscribe(data => {
        this.form.reset();

        this.toastrService.success('success', 'Item has been changed.');
      });
    } else {
      this.itemService.addItemToCollection(this.collectionId, this.form.value).subscribe(data => {
        this.form.reset();

        this.toastrService.success('success', 'Item has been added to collection.');
      });
    }
  }

  deleteField(field: CustomField) {

    this.formService.deleteField(field, this.form);
  }

  addField(field: CustomField) {

  this.formService.addField(field, this.form);
  }
}
