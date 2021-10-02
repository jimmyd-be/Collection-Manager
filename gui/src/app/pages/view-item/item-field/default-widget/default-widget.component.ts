import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-default-widget',
  templateUrl: './default-widget.component.html',
  styleUrls: ['./default-widget.component.scss'],
})
export class DefaultWidgetComponent {

  @Input() value: string;

  constructor() {
  }

}
