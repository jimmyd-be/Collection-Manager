import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-email-widget',
  templateUrl: './email-widget.component.html',
  styleUrls: ['./email-widget.component.scss']
})
export class EmailWidgetComponent implements OnInit {

  @Input() value: string;

  rating: number;

  constructor() {
  }

  ngOnInit() {
    this.rating = Number(this.value) / 2;
  }
}
