import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { CollectionService } from '../../Services/collection.service';
import { CustomFieldService } from '../../Services/custom-field.service';
import { CustomField } from '../../Entities/custom-field';
import { Collection } from '../../Entities/collection';
import { faList, faThList, faTh } from '@fortawesome/free-solid-svg-icons';
import { ItemService } from '../../Services/item.service';
import { Item } from '../../Entities/item';

@Component({
  selector: 'view-collection',
  templateUrl: './view-collection.component.html',
  styleUrls: ['./view-collection.component.scss']
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
  items: Item[];
  

  constructor(private route: ActivatedRoute, private collectionService: CollectionService, private customFieldService: CustomFieldService, private itemService: ItemService) { }

  ngOnInit() {

    this.firstLetterFilter = "#ABCDEFGHIJKLMNOPQRSTUVWQYZ".split('');

    this.route.paramMap.subscribe((params: ParamMap) => {
      if(params.has('id'))
      {
        this.collection = this.collectionService.getUserCollection(+params.get('id'));
        this.fields = this.customFieldService.getFieldsByCollection(+params.get('id'));
      }
    });
  }

  changeView(view: string): void{
    this.currentView = view;
  }

  changeLetterFilter(filter: string): void{
    this.currentLetterFilter = filter;
  }

  onScroll() {
    this.currentPage += 1;
    
    let newItems = this.itemService.getItemOfCollection(this.collection.id, this.currentPage, this.itemsPerPage);

    this.items.push(newItems);
  }

}
