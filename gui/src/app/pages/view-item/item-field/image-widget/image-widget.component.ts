import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-image-widget',
  templateUrl: './image-widget.component.html',
  styleUrls: ['./image-widget.component.scss'],
})
export class ImageWidgetComponent implements OnInit {

  @Input() value: string[];

  constructor() {
  }

  ngOnInit() {
  }

}
