import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CustomField } from '../Entities/custom-field';

@Injectable({
  providedIn: 'root',
})
export class FieldService {

  constructor(private http: HttpClient) {}

  getFieldsByCollection(id: number): Observable<CustomField[]> {
    return this.http.get<CustomField[]>('/field/collection/' + id);
  }
}
