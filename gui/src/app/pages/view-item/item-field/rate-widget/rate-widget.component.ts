import { Component, OnInit, Input } from '@angular/core';
import { typeWithParameters } from '@angular/compiler/src/render3/util';

@Component({
  selector: 'ngx-rate-widget',
  templateUrl: './rate-widget.component.html',
  styleUrls: ['./rate-widget.component.scss']
})
export class RateWidgetComponent implements OnInit {

  @Input() value: string;

  rating: number;

  constructor() { }

  ngOnInit() {
    this.rating = Number(this.value) / 2;
  }

}
