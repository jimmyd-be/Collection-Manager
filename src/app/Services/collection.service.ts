import { Injectable } from '@angular/core';
import { Collection } from '../Entities/collection';

@Injectable({
  providedIn: 'root'
})
export class CollectionService {

  constructor() { }


  getUserCollections(): Collection[]
  {
      return [new Collection(1, 'Movies', 'Movies', ['Ikke', 'jef'])];
  }

  getCollectionTypes(): string[]{
    return ['Books', 'Comics', 'Games', 'Magazines', 'Movies'];
  }
}
