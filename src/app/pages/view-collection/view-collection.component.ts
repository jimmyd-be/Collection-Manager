import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { CollectionService } from '../../Services/collection.service';
import { CustomFieldService } from '../../Services/custom-field.service';
import { CustomField } from '../../Entities/custom-field';
import { Collection } from '../../Entities/collection';

@Component({
  selector: 'view-collection',
  templateUrl: './view-collection.component.html',
  styleUrls: ['./view-collection.component.scss']
})
export class ViewCollectionComponent implements OnInit {

  collection: Collection; 
  fields: CustomField[];

  constructor(private route: ActivatedRoute, private collectionService: CollectionService, private customFieldService: CustomFieldService) { }

  ngOnInit() {

    this.route.paramMap.subscribe((params: ParamMap) => {
      if(params.has('id'))
      {
        this.collection = this.collectionService.getUserCollection(+params.get('id'));
        this.fields = this.customFieldService.getFieldsByCollection(+params.get('id'));
      }
    });
  }

}
