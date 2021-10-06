import {Directive} from '@angular/core';

@Directive()
export class ItemSearchDirective {

  constructor(
    public externalId: string,
    public name: string,
    public image: string,
    public releaseDate: string,
    public source: string,
    public url: string) {
  }

}
