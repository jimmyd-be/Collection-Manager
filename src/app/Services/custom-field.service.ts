import { Injectable } from '@angular/core';
import { CustomField } from '../Entities/custom-field';

@Injectable({
  providedIn: 'root',
})
export class CustomFieldService {

  constructor() { }

  getCustomFieldTypes(): string[] {
    return ['text', 'url', 'textarea', 'date', 'dropdown', 'checkBox', 'radio', 'email', 'rate', 'image'];
  }

  getLabelPositions(): string[] {
      return ['top', 'left', 'hidden'];
  }

  getFieldPositions(): string[] {
    return ['left', 'main', 'right'];
  }

  getFieldsByCollection(collectionId: Number): CustomField[] {

    return [new CustomField(5, 'Name', 'text', [], true, 'Give name of the item', 0, 'left', false, 'left', 'Name', ''),
    new CustomField(5, 'Type', 'dropdown', ['Movie', 'old movie'], true, 'Give name of the item', 0, 'left', false, 'left', 'Type', ''),
    new CustomField(5, 'Original', 'checkBox', [], false, 'Is original movie?', 0, 'left', false, 'left', 'Original', ''),
    new CustomField(5, 'url', 'url', [], true, 'Give url of the trailer', 0, 'main', false, 'left', 'Trailer', ''),
    new CustomField(5, 'note', 'textarea', [], true, 'Note of the movie', 0, 'main', false, 'left', 'Note', ''),
    new CustomField(5, 'Mail', 'email', [], true, 'Note of the movie', 0, 'main', false, 'left', 'Mail To', ''),
    new CustomField(5, 'rating', 'rate', [], true, 'Note of the movie', 0, 'main', false, 'left', 'rate movie', ''),
    new CustomField(5, 'cover', 'image', [], true, 'Note of the movie', 0, 'main', false, 'left', 'cover', ''),
    new CustomField(5, 'ReleaseDate', 'date', [], true, 'Note of the movie', 0, 'main', false, 'left', 'Release date', ''),
  ];
  }
}
