import { Injectable } from '@angular/core';
import { Collection } from '../Entities/collection';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class CollectionService {

  constructor(private http: HttpClient) {
  }

  editCollection(collection: Collection) {
    return this.http.post<Collection>('/collection/edit', collection);
  }

  deleteCollection(id: number) {
    return this.http.delete('/collection/' + id);
  }

  createCollection(collection: Collection) {
    return this.http.post<Collection>('/collection/add', collection);
  }

  getUserCollection(id: Number): Observable<Collection> {
    return this.http.get<Collection>('/collection/' + id);
  }

  getUserCollections(): Observable<Collection[]> {
      return this.http.get<Collection[]>('/collection/user');
  }

  getCollectionTypes(): string[] {
    return ['Books', 'Comics', 'Games', 'Magazines', 'Movies'];
  }
}
