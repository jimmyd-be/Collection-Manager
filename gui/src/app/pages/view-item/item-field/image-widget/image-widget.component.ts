import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'ngx-image-widget',
  templateUrl: './image-widget.component.html',
  styleUrls: ['./image-widget.component.scss'],
})
export class ImageWidgetComponent implements OnInit {

  @Input() value: string;

  constructor() { }

  ngOnInit() {}

}
