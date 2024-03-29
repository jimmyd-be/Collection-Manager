import { Injectable } from '@angular/core';
import { Item } from '../Entities/item';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ItemSearchDirective } from '../Entities/ItemSearch';

@Injectable({
  providedIn: 'root',
})
export class ItemService {

  constructor(private http: HttpClient) { }

  addItemToCollection(collectionId: number, arg1: string): any {
    return this.http.post('/item/add/collection/' + collectionId, arg1);
  }

    getItemOfCollection(collectionId: number, page: number, itemsPerPage: number, searchValue: string): Observable<Item[]> {
    return this.http.get<Item[]>('/item/get/collection/' + collectionId + '/' + page + '/' + itemsPerPage + '?query=' + searchValue);
  }

  deleteItemFromCollection(id: number, collectionId: number) {
    return this.http.delete('/item/' + id + '/collection/' + collectionId);
  }

  editItemToCollection(itemid: number, collectionId: number, value: any) {
    return this.http.patch('/item/edit/' + itemid + '/' + collectionId, value);
  }

  getItemById(itemId: number): Observable<Item> {
    return this.http.get<Item>('/item/get/' + itemId);
  }

  searchItem(search: string, type: string): Observable<ItemSearchDirective[]> {
    return this.http.get<ItemSearchDirective[]>('/item/external/' + type + '?search=' + search);
  }

  addExternalItemToCollection(collectionId: number, source: string, externalId: string): Observable<any> {
    return this.http.post('/item/external/add/collection/' + collectionId + '/' + source + '/' + externalId, '');
  }

  countItemOfCollection(collectionId: number, search: string): Observable<any> {
    return this.http.get('/item/count/collection/' + collectionId + '?search=' + search);
  }
}
