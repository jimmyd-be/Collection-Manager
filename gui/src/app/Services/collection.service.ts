import { Injectable } from '@angular/core';
import { Collection } from '../Entities/collection';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CollectionShare } from '../Entities/collectionShare';
import { UserCollection } from '../Entities/UserCollection';


@Injectable({
  providedIn: 'root',
})
export class CollectionService {

  constructor(private http: HttpClient) {
  }

  deleteUserFromCollection(userId: Number, collectionId: number) {
    return this.http.delete('/collection/' + collectionId + '/user/' + userId);
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

  getUsers(id: Number): Observable<UserCollection[]> {
    return this.http.get<UserCollection[]>('/collection/' + id + '/users');
  }

  shareCollection(collectionShare: CollectionShare, collectionId: Number) {
    return this.http.post('/collection/' + collectionId + '/share', collectionShare);
  }

  getCollectionTypes(): string[] {
    return ['Books', 'Comics', 'Games', 'Magazines', 'Movies'];
  }
}
