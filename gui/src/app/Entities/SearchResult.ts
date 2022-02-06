import {Item} from "./item";

export class SearchResult {

  constructor(public collectionId: number,
              public collectionName: string,
              public items: Item[]) {
  }
}
