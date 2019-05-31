import { Injectable } from '@angular/core';
import { Item } from '../Entities/item';

@Injectable({
  providedIn: 'root',
})
export class ItemService {

  constructor() { }

  addItemToCollection(collectionId: Number, arg1: string): any {
    throw new Error('Method not implemented.');
  }

  getItemOfCollection(collectionId: number, page: number, itemsPerPage: number): Item[] {
    return [new Item('The Hobbit', null), new Item('The Matrix',
    'http://upload.wikimedia.org/wikipedia/en/9/9a/The_Matrix_soundtrack_cover.jpg')];
  }
}
