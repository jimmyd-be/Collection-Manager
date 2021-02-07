import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CollectionService} from '../../Services/collection.service';
import {CustomField} from '../../Entities/custom-field';
import {Collection} from '../../Entities/collection';
import {faEdit, faList, faTh, faTrash} from '@fortawesome/free-solid-svg-icons';
import {ItemService} from '../../Services/item.service';
import {Item} from '../../Entities/item';
import {FieldService} from '../../Services/field.service';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {ItemDialogComponent} from '../item-dialog/item-dialog.component';
import {ItemFieldDirective} from '../../Entities/ItemField';
import {ConfirmationDialogComponent} from '../confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-view-collection',
  templateUrl: './view-collection.component.html',
  styleUrls: ['./view-collection.component.scss'],
})
export class ViewCollectionComponent implements OnInit {

  listIcon = faList;
  cardIcon = faTh;
  deleteIcon = faTrash;
  editIcon = faEdit;

  id: number;
  itemsPerPage = 50;
  currentPage = 0;
  currentView = 'list';
  currentLetterFilter = 'ALL';

  firstLetterFilter: string[];

  collection: Collection;
  fields: CustomField[];
  items: Item[] = Array();
  searchValue = '';

  constructor(private route: ActivatedRoute,
              private collectionService: CollectionService,
              private itemService: ItemService,
              private fieldService: FieldService,
              private dialogService: NbDialogService,
              private router: Router,
              private toastrService: NbToastrService) {
  }

  ngOnInit() {

    if (localStorage.getItem('collectionView') !== null) {
      this.currentView = localStorage.getItem('collectionView');
    }

    this.firstLetterFilter = '#ABCDEFGHIJKLMNOPQRSTUVWQYZ'.split('');

    if (this.id == null) {
      this.loadData();
    }

    this.router.events.subscribe((val) => {
      this.loadData();
    });
  }

  search(event: any) {

    this.searchValue = event.target.value;
    this.items = [];

    this.itemService.getItemOfCollection(this.id, this.currentPage, this.itemsPerPage, this.searchValue).subscribe(items => {
      for (const item of items) {
        this.items.push(item);
      }
    });
  }

  getImage(item: Item): string {

    if (item.image !== null && item.image !== '') {
      return item.image;
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
    this.dialogService.open(ItemDialogComponent, {context: new ItemFieldDirective(item, this.fields, this.collection)})
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
            this.toastrService.success(item.name + ' has been removed from the collection.');
            const index = this.items.indexOf(item, 0);
            if (index > -1) {
              this.items.splice(index, 1);
            }
          });
        }
      },
      error => {
        this.toastrService.danger(item.name + ' could not be deleted because of an error!');
      });
  }

  onScroll() {
    this.currentPage += 1;

    this.itemService.getItemOfCollection(this.collection.id, this.currentPage, this.itemsPerPage, this.searchValue).subscribe(items => {
      for (const item of items) {
        this.items.push(item);
      }
    });

  }

  private loadData() {
    const currentId = Number(this.route.snapshot.paramMap.get('id'));

    if (currentId !== null && this.id !== currentId) {

      this.id = currentId;

      this.fieldService.getFieldsByCollection(currentId).subscribe(data => {
        this.fields = data;
      });

      this.collectionService.getUserCollection(currentId).subscribe(data => {
        this.collection = data;
      });

      this.items = [];

      this.itemService.getItemOfCollection(currentId, this.currentPage, this.itemsPerPage, this.searchValue).subscribe(items => {
        for (const item of items) {
          this.items.push(item);
        }
      });
    }
  }

}
