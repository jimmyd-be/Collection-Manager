import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-rate-widget',
  templateUrl: './rate-widget.component.html',
  styleUrls: ['./rate-widget.component.scss'],
})
export class RateWidgetComponent implements OnInit {

  @Input() value: string;

  rating: number;

  constructor() { }

  ngOnInit() {
    this.rating = Number(this.value) / 2;
  }

}
