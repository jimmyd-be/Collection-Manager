import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CollectionService } from '../../Services/collection.service';
import { CustomField } from '../../Entities/custom-field';
import { Collection } from '../../Entities/collection';
import { faList, faTh } from '@fortawesome/free-solid-svg-icons';
import { ItemService } from '../../Services/item.service';
import { Item } from '../../Entities/item';

@Component({
  selector: 'ngx-view-collection',
  templateUrl: './view-collection.component.html',
  styleUrls: ['./view-collection.component.scss'],
})
export class ViewCollectionComponent implements OnInit {

  listIcon = faList;
  cardIcon = faTh;

  itemsPerPage: number = 50;
  currentPage: number = 0;
  currentView: string = 'list';
  currentLetterFilter: string = 'ALL';

  firstLetterFilter: string[] ;

  collection: Collection;
  fields: CustomField[];
  items: Item[] = Array();

  constructor(private route: ActivatedRoute, private collectionService: CollectionService,
    private itemService: ItemService) { }

  ngOnInit() {

    this.firstLetterFilter = '#ABCDEFGHIJKLMNOPQRSTUVWQYZ'.split('');

    const id = Number(this.route.snapshot.paramMap.get('id'));

    if (id !== null) {

      this.collectionService.getUserCollection(id).subscribe(data => {
        this.collection = data;
        this.fields = data.fields;
      });

      this.itemService.getItemOfCollection(id, this.currentPage, this.itemsPerPage).subscribe(items => {

        for (const item of items)
        {
          this.items.push(item);
        }
      });
    }
  }

  changeView(view: string): void {
    this.currentView = view;
  }

  changeLetterFilter(filter: string): void {
    this.currentLetterFilter = filter;
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
