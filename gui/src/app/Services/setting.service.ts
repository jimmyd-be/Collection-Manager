import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Setting } from '../Entities/Setting';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SettingService {

  constructor(private http: HttpClient) {}

  getAllSettings(): Observable<Setting[]> {
    return this.http.get<Setting[]>('/admin/settings');
  }

  saveSettings(values: Setting[]): Observable<object> {
    return this.http.patch<Setting[]>('/admin/settings', values);
  }
}
