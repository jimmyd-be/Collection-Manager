import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-url-widget',
  templateUrl: './url-widget.component.html',
  styleUrls: ['./url-widget.component.scss']
})
export class UrlWidgetComponent implements OnInit {

  @Input() value: string;

  url: string;
  label: string;

  constructor() { }

  ngOnInit(): void {

    const values = String(this.value).split('||');

    this.url = values[0];

    if (values.length === 2) {
      this.label = values[1];
    }
  }

}
