import { Injectable } from '@angular/core';
import { Collection } from '../Entities/collection';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CollectionService {

  

  constructor(private http: HttpClient) {
  }

  editCollection(collection: Collection) {
    throw new Error("Method not implemented.");
  }
  
  deleteCollection(id: number) {
    return this.http.delete('/collection/' + id);
  }

  createCollection(collection: Collection) {
    return this.http.post<Collection>('/collection/add', collection);
  }

  getUserCollection(id: Number): Collection{
    if(id == 1)
    {
    return new Collection(1, 'Movies', 'Movies', ['Ikke', 'jef'])
    }
    else if(id == 2)
    {
      return new Collection(2, 'Games', 'Games', ['Ikke', 'jef']);
    }
  }


  getUserCollections(): Observable<Collection[]>
  {
      return this.http.get<Collection[]>('/collection/user');
  }

  getCollectionTypes(): string[]{
    return ['Books', 'Comics', 'Games', 'Magazines', 'Movies'];
  }
}
