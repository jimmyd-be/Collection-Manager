import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CollectionService} from '../../Services/collection.service';
import {CustomField} from '../../Entities/custom-field';
import {Collection} from '../../Entities/collection';
import {faList, faTh} from '@fortawesome/free-solid-svg-icons';
import {ItemService} from '../../Services/item.service';
import {Item} from '../../Entities/item';
import {FieldService} from '../../Services/field.service';
import {ItemDialogComponent} from '../item-dialog/item-dialog.component';
import {ItemFieldDirective} from '../../Entities/ItemField';
import {ConfirmationService, LazyLoadEvent, MessageService} from "primeng/api";
import {DialogService} from "primeng/dynamicdialog";

@Component({
  selector: 'app-view-collection',
  templateUrl: './view-collection.component.html',
  styleUrls: ['./view-collection.component.scss'],
})
export class ViewCollectionComponent implements OnInit {

  listIcon = faList;
  cardIcon = faTh;

  id: number;
  itemsPerPage = 50;
  currentPage = 0;
  currentView = 'list';

  collection: Collection;
  fields: CustomField[];
  // items: Item[] = Array();
  virtualItems: Item[];
  searchValue = '';

  constructor(private route: ActivatedRoute,
              private collectionService: CollectionService,
              private itemService: ItemService,
              private fieldService: FieldService,
              private confirmationService: ConfirmationService,
              private router: Router,
              private messageService: MessageService,
              public dialogService: DialogService) {
  }

  ngOnInit() {

    if (localStorage.getItem('collectionView') !== null) {
      this.currentView = localStorage.getItem('collectionView');
    }
    if (this.id == null) {
      this.loadData();
    }

    this.router.events.subscribe((val) => {
      this.loadData();
    });
  }

  search(event: any) {

    this.searchValue = event.target.value;
    // this.items = [];
    //
    // this.itemService.getItemOfCollection(this.id, this.currentPage, this.itemsPerPage, this.searchValue).subscribe(items => {
    //   for (const item of items) {
    //     this.items.push(item);
    //   }
    // });
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

  openModal(item: Item) {
    this.dialogService.open(ItemDialogComponent, {data: new ItemFieldDirective(item, this.fields, this.collection)})
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

    // this.confirmationService.confirm({
    //   message: 'Are you sure that you want to perform this action?',
    //   accept: () => {
    //     this.itemService.deleteItemFromCollection(item.id, this.collection.id).subscribe(data => {
    //       this.messageService.add({severity:'success', summary: item.name + ' has been removed from the collection.'});
    //       const index = this.items.indexOf(item, 0);
    //       if (index > -1) {
    //         this.items.splice(index, 1);
    //       }
    //     });
    //   }
    // });

  }

  onScroll(event: LazyLoadEvent) {
    this.currentPage += 1;

    this.itemService.getItemOfCollection(this.collection.id, event.first, event.rows + event.first, this.searchValue).subscribe(items => {
      /*for (const item of items) {
        this.items.push(item);
      }*/

      var loadedItems = items.slice(event.first, event.first + event.rows);
      //populate page of virtual cars
      Array.prototype.splice.apply(this.virtualItems, [...[event.first, event.rows], ...loadedItems]);

      //trigger change detection
      this.virtualItems = [...this.virtualItems];
    });

  }

  private loadData() {
    const currentId = Number(this.route.snapshot.paramMap.get('id'));

    if (currentId !== null && this.id !== currentId) {

      this.id = currentId;

      this.itemService.countItemOfCollection(this.id).subscribe(count =>
        this.virtualItems = Array.from({length: count})
      );

      this.fieldService.getFieldsByCollection(currentId).subscribe(data => {
        this.fields = data;
      });

      this.collectionService.getUserCollection(currentId).subscribe(data => {
        this.collection = data;
      });

      // this.items = [];
      //
      // this.itemService.getItemOfCollection(currentId, this.currentPage, this.itemsPerPage, this.searchValue).subscribe(items => {
      //   for (const item of items) {
      //     this.items.push(item);
      //   }
      // });
    }
  }

  getGenres(item: Item) {

    let genreField = this.fields.filter(field => field.name==="genre");

    return item.data
      .filter(data => data.fieldId === genreField[0].id)
      .map(data => data.value)
      .join(", ");
  }
}
