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

    return [new CustomField("Name", "text", [], true, "Give name of the item", 0, "", false, "left", "Name", ""),
    new CustomField("Type", "dropdown", ['Movie', 'old movie'], true, "Give name of the item", 0, "", false, "left", "Type", ""),
    new CustomField("Original", "checkBox", [], false, "Is original movie?", 0, "", false, "left", "Original", ""),
    new CustomField("Radio", "radio", ['Yes', 'No', 'Maybe'], true, "is this a good movie?", 0, "", false, "left", "Good movie?", ""),
    new CustomField("url", "url", [], true, "Give url of the trailer", 0, "", false, "left", "Trailer", ""),
    new CustomField("note", "textarea", [], true, "Note of the movie", 0, "", false, "left", "Note", ""),
    new CustomField("Mail", "email", [], true, "Note of the movie", 0, "", false, "left", "Mail To", ""),
    new CustomField("rating", "rate", [], true, "Note of the movie", 0, "", false, "left", "rate movie", ""),
    new CustomField("cover", "image", [], true, "Note of the movie", 0, "", false, "left", "cover", ""),
    new CustomField("ReleaseDate", "date", [], true, "Note of the movie", 0, "", false, "left", "Release date", "")
  ];
  }
}
