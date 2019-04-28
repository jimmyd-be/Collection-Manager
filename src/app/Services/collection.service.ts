import { Injectable } from '@angular/core';
import { Collection } from '../Entities/collection';

@Injectable({
  providedIn: 'root'
})
export class CollectionService {
  editCollection(collection: Collection) {
    throw new Error("Method not implemented.");
  }
  
  deleteCollection(id: number) {
    throw new Error("Method not implemented.");
  }

  createCollection(collection: Collection) {
    throw new Error("Method not implemented.");
  }

  constructor() { }

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


  getUserCollections(): Collection[]
  {
      return [new Collection(1, 'Movies', 'Movies', ['Ikke', 'jef']),new Collection(2, 'Games', 'Games', ['Ikke', 'jef'])];
  }

  getCollectionTypes(): string[]{
    return ['Books', 'Comics', 'Games', 'Magazines', 'Movies'];
  }
}
