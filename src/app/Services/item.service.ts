import { Injectable } from '@angular/core';
import { Item } from '../Entities/item';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ItemService {

  constructor(private http: HttpClient) { }

  addItemToCollection(collectionId: Number, arg1: string): any {
    return this.http.post('/item/add/collection/' + collectionId, arg1);
  }

  getItemOfCollection(collectionId: number, page: number, itemsPerPage: number): Item[] {
    return [new Item('The Hobbit', null), new Item('The Matrix',
    'http://upload.wikimedia.org/wikipedia/en/9/9a/The_Matrix_soundtrack_cover.jpg')];
  }
}
