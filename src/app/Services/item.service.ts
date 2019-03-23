import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  
  constructor() { }

  addItemToCollection(collectionId: Number, arg1: string): any {
    throw new Error("Method not implemented.");
  }
}
