import { Injectable } from '@angular/core';
import { Item } from '../Entities/item';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ItemSearch } from '../Entities/ItemSearch';

@Injectable({
  providedIn: 'root',
})
export class ItemService {

  constructor(private http: HttpClient) { }

  addItemToCollection(collectionId: Number, arg1: string): any {
    return this.http.post('/item/add/collection/' + collectionId, arg1);
  }

  getItemOfCollection(collectionId: number, page: number, itemsPerPage: number): Observable<Item[]> {
    return this.http.get<Item[]>('/item/get/collection/' + collectionId + '/' + page + '/' + itemsPerPage);
  }

  deleteItemFromCollection(id: number, collectionId: number) {
    return this.http.delete('/item/' + id + '/collection/' + collectionId);
  }

  editItemToCollection(itemid: number, collectionId: number, value: any) {
    return this.http.post('/item/edit/' + itemid + '/' + collectionId, value);
  }

  getItemById(itemId: Number): Observable<Item> {
    return this.http.get<Item>('/item/get/' + itemId);
  }

  searchItem(search: string): Observable<ItemSearch[]> {
    return this.http.get<ItemSearch[]>('/item/external/movie?search=' + search);
  }

  addExternalItemToCollection(collectionId: Number, source: string, externalId: string): Observable<any> {
    return this.http.post('/item/external/add/collection/' + collectionId + '/' + source + '/' + externalId, '');
  }

}
