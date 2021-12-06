import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Route, Router} from "@angular/router";
import {SearchService} from "../../Services/search.service";
import {SearchResult} from "../../Entities/SearchResult";
import {FieldService} from "../../Services/field.service";
import {CustomField} from "../../Entities/custom-field";
import {Item} from "../../Entities/item";
import {ConfirmationDialogComponent} from "../confirmation-dialog/confirmation-dialog.component";
import {NbDialogService, NbToastrService} from "@nebular/theme";
import {ItemService} from "../../Services/item.service";

@Component({
  selector: 'app-global-search',
  templateUrl: './global-search.component.html',
  styleUrls: ['./global-search.component.scss']
})
export class GlobalSearchComponent implements OnInit {

  searchTerm: string;
  result: SearchResult[];
  fields = new Map<number, CustomField[]>();
  items = new Map<number, Item[]>();

  constructor(private route: ActivatedRoute,
              private searchService: SearchService,
              private fieldService: FieldService,
              private router: Router,
              private dialogService: NbDialogService,
              private itemService: ItemService,
              private toastrService: NbToastrService) {
    this.searchTerm = this.route.snapshot.queryParamMap.get("searchTerm");

    this.searchService.globalSearch(this.searchTerm)
      .subscribe(data => this.result = data)
      .unsubscribe();
  }

  ngOnInit(): void {
    this.searchTerm = this.route.snapshot.queryParamMap.get("searchTerm");

    this.searchService.globalSearch(this.searchTerm)
      .subscribe(data => {
        this.result = data;

        this.result.forEach(result => this.items.set(result.collectionId, result.items));

        this.result.forEach(result => this.fieldService.getFieldsByCollection(result.collectionId)
          .subscribe(fields => this.fields.set(result.collectionId, fields)));
      });
  }

  editItem(item: Item, collectionId: number) {
    this.router.navigate(['/pages/item/edit/' + collectionId + '/' + item.id]);
  }

  deleteItem(item: Item, collectionId: number) {

    this.dialogService.open(ConfirmationDialogComponent)
      .onClose.subscribe(response => {
        if (response === 'delete') {
          this.itemService.deleteItemFromCollection(item.id, collectionId).subscribe(data => {
            this.toastrService.success(item.name + ' has been removed from the collection.');
            const index = this.items.get(collectionId).indexOf(item, 0);
            if (index > -1) {
              this.items.get(collectionId).splice(index, 1);
            }
          });
        }
      },
      error => {
        this.toastrService.danger(item.name + ' could not be deleted because of an error!');
      });
  }

}
