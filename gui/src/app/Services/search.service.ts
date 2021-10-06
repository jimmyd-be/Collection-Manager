import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {SearchResult} from "../Entities/SearchResult";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private httpClient: HttpClient) { }

  globalSearch(searchTerm: string): Observable<SearchResult[]> {
    return this.httpClient.get<SearchResult[]>('/search/' + searchTerm);
  }
}
