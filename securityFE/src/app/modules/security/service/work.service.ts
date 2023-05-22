import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Work } from '../model/work.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WorkService {
  apiHost: string = 'http://localhost:8082/';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) { }


}
