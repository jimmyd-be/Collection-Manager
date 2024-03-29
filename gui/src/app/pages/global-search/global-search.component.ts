import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Route, Router} from "@angular/router";
import {SearchService} from "../../Services/search.service";
import {SearchResult} from "../../Entities/SearchResult";
import {FieldService} from "../../Services/field.service";
import {CustomField} from "../../Entities/custom-field";
import {Item} from "../../Entities/item";
import {ItemService} from "../../Services/item.service";
import {ConfirmationService, MessageService} from "primeng/api";

@Component({
  selector: 'app-global-search',
  templateUrl: './global-search.component.html',
  styleUrls: ['./global-search.component.scss'],

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
              private confirmationService: ConfirmationService,
              private itemService: ItemService,
              private messageService: MessageService) {
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

    this.confirmationService.confirm({
      message: 'Are you sure that you want to perform this action?',
      accept: () => {
        this.itemService.deleteItemFromCollection(item.id, collectionId).subscribe(data => {
          this.messageService.add({severity:'success', summary:item.name + ' has been removed from the collection.'});
          const index = this.items.get(collectionId).indexOf(item, 0);
          if (index > -1) {
            this.items.get(collectionId).splice(index, 1);
          }
        });
      }
    });

  }

}
