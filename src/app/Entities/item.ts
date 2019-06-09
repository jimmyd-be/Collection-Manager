import { ItemData } from "./ItemData";

export class Item {

    constructor(public id: number, public name: string, public image: string, public itemData: ItemData[]) {}
}
