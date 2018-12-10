import { Injectable } from '@angular/core';

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
}
