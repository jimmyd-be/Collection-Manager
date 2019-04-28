import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { CollectionService } from '../../Services/collection.service';
import { CustomFieldService } from '../../Services/custom-field.service';
import { CustomField } from '../../Entities/custom-field';
import { Collection } from '../../Entities/collection';
import { faList, faThList, faTh } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'view-collection',
  templateUrl: './view-collection.component.html',
  styleUrls: ['./view-collection.component.scss']
})
export class ViewCollectionComponent implements OnInit {

  listIcon = faList;
  cardIcon = faTh;

  currentView: string = 'list';
  currentLetterFilter: string = 'ALL';

  firstLetterFilter: string[] ;

  collection: Collection; 
  fields: CustomField[];
  

  constructor(private route: ActivatedRoute, private collectionService: CollectionService, private customFieldService: CustomFieldService) { }

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

}
