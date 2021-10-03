import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-image-widget',
  templateUrl: './image-widget.component.html',
  styleUrls: ['./image-widget.component.scss'],
})
export class ImageWidgetComponent {

  @Input() value: string[];

  constructor() {
  }

  getImage(): string {

    if (this.value !== null && this.value[0] !== null && this.value[0] !== '') {
      return this.value[0];
    }

    return '../../../../../assets/images/noImage.jpeg';
  }

}
