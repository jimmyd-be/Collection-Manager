import {Component, OnInit, ViewChild} from '@angular/core';
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
import {VirtualScroller} from "primeng/virtualscroller";

@Component({
  selector: 'app-view-collection',
  templateUrl: './view-collection.component.html',
  styleUrls: ['./view-collection.component.scss'],
})
export class ViewCollectionComponent implements OnInit {

  @ViewChild('vs') vs: VirtualScroller;

  listIcon = faList;
  cardIcon = faTh;

  id: number;
  itemsPerPage = 50;

  totalItems: number;

  collection: Collection;
  fields: CustomField[];
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
    if (this.id == null) {
      this.loadData();
    }

    this.router.events.subscribe((val) => {
      this.loadData();
    });
  }

  search(event: any) {

    this.searchValue = event.target.value;

    // this.itemService.countItemOfCollection(this.id, this.searchValue).subscribe(count =>
    //   this.virtualItems = Array.from({length: count})
    // );

    // this.vs.scrollToIndex(0, 'smooth');
    // this.vs.onScrollIndexChange(0);
    //
    // this.virtualItems.splice(0,this.virtualItems.length)
    //
    // this.itemService.countItemOfCollection(this.id, this.searchValue).subscribe(count => {
    //   this.virtualItems = Array.from({length: count});
    //   this.totalItems = count;
    // });


    // this.virtualItems = [...this.virtualItems];

  }

  getImage(item: Item): string {

    if (item.image !== null && item.image !== '') {
      return item.image;
    }

    return '../../../assets/images/noImage.jpeg';
  }

  openModal(item: Item) {

    let itemFieldDirective = new ItemFieldDirective(item, this.fields, this.collection);

    this.dialogService.open(ItemDialogComponent, {
      data: {
        itemFieldDirective: itemFieldDirective
      },
      width: "70%",
      dismissableMask: true,
      header: item.name
    })
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

    this.confirmationService.confirm({
      message: 'Are you sure that you want to perform this action?',
      accept: () => {
        this.itemService.deleteItemFromCollection(item.id, this.collection.id).subscribe(data => {
          this.messageService.add({severity:'success', summary: item.name + ' has been removed from the collection.'});
          const index = this.virtualItems.indexOf(item, 0);
          if (index > -1) {
            this.virtualItems.splice(index, 1);
          }
        });
      }
    });

  }

  onScroll(event: LazyLoadEvent) {

    const collectionId = Number(this.route.snapshot.paramMap.get('id'));

    let first = event.first;
    let rows = event.rows;
    this.loadItems(collectionId, first, rows);

  }

  private loadItems(collectionId: number, first: number, rows: number) {
    this.itemService.getItemOfCollection(collectionId, first, rows + first, this.searchValue).subscribe(items => {
      this.virtualItems = items;
    });
  }

  private loadData() {
    const currentId = Number(this.route.snapshot.paramMap.get('id'));
    this.id = currentId;

    this.itemService.countItemOfCollection(this.id, this.searchValue).subscribe(count =>
      this.totalItems = count
    );

    this.fieldService.getFieldsByCollection(currentId).subscribe(data => {
      this.fields = data;
    });

    this.collectionService.getUserCollection(currentId).subscribe(data => {
      this.collection = data;
    });
    this.loadItems(this.id, 0, 50);
  }

  getGenres(item: Item) {

    let genreField = this.fields.filter(field => field.name === "genre");

    return item.data
      .filter(data => data.fieldId === genreField[0].id)
      .map(data => data.value)
      .join(", ");
  }
}
