import { Injectable } from '@angular/core';
import { Item } from '../Entities/item';

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  
  constructor() { }

  addItemToCollection(collectionId: Number, arg1: string): any {
    throw new Error("Method not implemented.");
  }

  getItemOfCollection(collectionId: number, page: number, itemsPerPage: number): Item[]
  {
    throw new Error("Method not implemented.");
  }
}
