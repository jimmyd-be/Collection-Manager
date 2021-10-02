import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-default-widget',
  templateUrl: './default-widget.component.html',
  styleUrls: ['./default-widget.component.scss'],
})
export class DefaultWidgetComponent implements OnInit {

  @Input() value: string;

  constructor() {
  }

  ngOnInit() {
  }

}
