import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Work } from '../model/work.model';
import { Observable } from 'rxjs';
import { AppUser } from '../model/appUser.model';
import { JsonPipe } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class WorkService {
  apiHost: string = 'https://localhost:8082/';
  baseApiUrl: string = 'https://localhost:8082/api/v1/work';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });


  constructor(private https: HttpClient) { }

  getAllWorkersByProject(projectId: number): Observable<AppUser[]> {

    //const url = '${this.baseApiUrl}//workersByProject/${projectId}';
    const url = `${this.baseApiUrl}/workersByProject/`+projectId;
    console.log("URLLL"+url);
    return this.https.get<AppUser[]>(url);
  }

  addWorkersToProjects(payload:any) : Observable<void> {
    return this.https.post<void>(`${this.baseApiUrl}/addWorkersToProjects`, JSON.stringify(payload), {headers: this.headers});

  }

  getWorksByWorkerId(workerId: number): Observable<Work[]> {
    const url = `${this.baseApiUrl}/${workerId}`;
    return this.https.get<Work[]>(url);
  }

  getCurrentWorksByWorkerId(workerId: number): Observable<Work[]> {
    const url = `${this.baseApiUrl}/current/${workerId}`;
    return this.https.get<Work[]>(url);
  }

  getPastWorksByWorkerId(workerId: number): Observable<Work[]> {
    const url = `${this.baseApiUrl}/past/${workerId}`;
    return this.https.get<Work[]>(url);
  }

  updateWorkDescription(id: number, updatedWork: Work): Observable<string> {
    const url = `${this.baseApiUrl}/description/${id}`;
    return this.https.put<string>(url, updatedWork, { headers: this.headers });
  }

  dismissWorker(id: number): Observable<any>{
    return this.https.put<any>(this.apiHost + '/dismiss/' + id, {headers: this.headers});
  }

}
