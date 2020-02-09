import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'ngx-default-widget',
  templateUrl: './default-widget.component.html',
  styleUrls: ['./default-widget.component.scss'],
})
export class DefaultWidgetComponent implements OnInit {

  @Input() value: string;

  constructor() { }

  ngOnInit() {
  }

}
