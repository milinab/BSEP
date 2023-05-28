import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Project } from '../model/project.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  apiHost: string = 'https://localhost:8082/';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private https: HttpClient) { }

  getProjects(): Observable<Project[]> {
    return this.https.get<Project[]>(this.apiHost + 'api/v1/project', {headers: this.headers});
  }

  createProject(project: Project): Observable<Project> {
    return this.https.post<Project>(this.apiHost + 'api/v1/project', project, {headers: this.headers});
  }
}
