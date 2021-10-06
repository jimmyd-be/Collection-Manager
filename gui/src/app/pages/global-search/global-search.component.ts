import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Route} from "@angular/router";
import {SearchService} from "../../Services/search.service";
import {SearchResult} from "../../Entities/SearchResult";

@Component({
  selector: 'app-global-search',
  templateUrl: './global-search.component.html',
  styleUrls: ['./global-search.component.scss']
})
export class GlobalSearchComponent implements OnInit {

  searchTerm: string;
  result: SearchResult[];

  constructor(private route: ActivatedRoute,
              private searchService: SearchService) {
    this.searchTerm = this.route.snapshot.queryParamMap.get("searchTerm");

    this.searchService.globalSearch(this.searchTerm)
      .subscribe(data => this.result = data)
      .unsubscribe();
  }

  ngOnInit(): void {
    this.searchTerm = this.route.snapshot.queryParamMap.get("searchTerm");

    this.searchService.globalSearch(this.searchTerm)
      .subscribe(data => this.result = data)
      .unsubscribe();
  }

}
