import { Injectable } from '@angular/core';
import { CustomField } from '../Entities/custom-field';

@Injectable({
  providedIn: 'root'
})
export class CustomFieldService {

  constructor() { }

  getCustomFieldTypes(): string[]
  {
    return ['number', 'text', 'url', 'text area', 'date'];
  }

  getLabelPositions(): string[]
  {
      return ['top', 'left', 'hidden'];
  }

  getFieldPositions(): string[]{
    return ['left', 'main', 'right'];
  }

  getFieldsByCollection(collectionId: Number): CustomField[]{

    return [new CustomField("Name", "text", "", true, "Give name of the item", 0, "", false, "left", "Name")];
  }
}
