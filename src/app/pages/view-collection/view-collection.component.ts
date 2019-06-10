import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CollectionService } from '../../Services/collection.service';
import { CustomField } from '../../Entities/custom-field';
import { Collection } from '../../Entities/collection';
import { faList, faTh, faTrash, faEdit } from '@fortawesome/free-solid-svg-icons';
import { ItemService } from '../../Services/item.service';
import { Item } from '../../Entities/item';
import { FieldService } from '../../Services/field.service';
import { ItemData } from '../../Entities/ItemData';
import { NbDialogService } from '@nebular/theme';
import { ItemDialogComponent } from '../item-dialog/item-dialog.component';
import { ItemField } from '../../Entities/ItemField';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'ngx-view-collection',
  templateUrl: './view-collection.component.html',
  styleUrls: ['./view-collection.component.scss'],
})
export class ViewCollectionComponent implements OnInit {

  listIcon = faList;
  cardIcon = faTh;
  deleteIcon = faTrash;
  editIcon = faEdit;

  itemsPerPage: number = 50;
  currentPage: number = 0;
  currentView: string = 'list';
  currentLetterFilter: string = 'ALL';

  firstLetterFilter: string[] ;

  collection: Collection;
  fields: CustomField[];
  items: Item[] = Array();

  constructor(private route: ActivatedRoute, private collectionService: CollectionService,
    private itemService: ItemService, private fieldService: FieldService, private dialogService: NbDialogService,
    private router: Router) { }

  ngOnInit() {

    if(localStorage.getItem('collectionView') !== null) {
      this.currentView = localStorage.getItem('collectionView');
    }

    this.firstLetterFilter = '#ABCDEFGHIJKLMNOPQRSTUVWQYZ'.split('');

    const id = Number(this.route.snapshot.paramMap.get('id'));

    if (id !== null) {

      this.fieldService.getFieldsByCollection(id).subscribe(data => {
        this.fields = data;
      });

      this.collectionService.getUserCollection(id).subscribe(data => {
        this.collection = data;
      });

      this.itemService.getItemOfCollection(id, this.currentPage, this.itemsPerPage).subscribe(items => {

        for (const item of items)
        {
          this.items.push(item);
        }
      });
    }
  }

  getImage(item: Item): string {

    const coverField = this.fields.filter((field: CustomField) => {
      return field.name === 'cover';
    });

    if (coverField != null && coverField.length > 0) {
      const dataValue = item.data.filter((data: ItemData) => {
        return data.fieldId === coverField[0].id;
      });

      if (dataValue != null && dataValue.length > 0 && dataValue[0].value && dataValue[0].value.length > 0) {
        return dataValue[0].value;
      }
    }

    return '../../../assets/images/noImage.jpeg';
  }

  changeView(view: string): void {
    this.currentView = view;
    localStorage.setItem('collectionView', view);
  }

  changeLetterFilter(filter: string): void {
    this.currentLetterFilter = filter;
  }

  openModal(item: Item) {
    this.dialogService.open(ItemDialogComponent, {context: new ItemField(item, this.fields, this.collection)})
    .onClose.subscribe(
      data => {
        if (data === 'delete') {
          this.deleteItem(item);
        } else if (data === 'edit') {
          this.editItem(item);
        }
      });
  }

  editItem(item: Item) {
    this.router.navigate(['/pages/item/edit/' + this.collection.id + '/' + item.id]);
  }

  deleteItem(item: Item) {

    this.dialogService.open(ConfirmationDialogComponent)
    .onClose.subscribe(response => {
        if (response === 'delete') {
          this.itemService.deleteItemFromCollection(item.id, this.collection.id).subscribe(data => {
            const index = this.items.indexOf(item, 0);
            if (index > -1) {
              this.items.splice(index, 1);
            }
            });
        }
    });
  }

  onScroll() {
    this.currentPage += 1;

    this.itemService.getItemOfCollection(this.collection.id, this.currentPage, this.itemsPerPage).subscribe(items => {
      for (const item of items)
        {
          this.items.push(item);
        }
    });

  }

}
