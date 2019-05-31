import { Injectable } from '@angular/core';
import { CustomField } from '../Entities/custom-field';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CustomFieldService {

  constructor(private http: HttpClient) {
  }

  getCustomFieldTypes(): string[] {
    return ['text', 'url', 'textarea', 'date', 'dropdown', 'checkBox', 'radio', 'email', 'rate', 'image'];
  }

  getLabelPositions(): string[] {
      return ['top', 'left', 'hidden'];
  }

  getFieldPositions(): string[] {
    return ['left', 'main', 'right'];
  }

  getFieldsByCollection(collectionId: Number): Observable<CustomField[]> {

    return this.http.get<CustomField[]>('/field/collection/' + collectionId);
  }
}
