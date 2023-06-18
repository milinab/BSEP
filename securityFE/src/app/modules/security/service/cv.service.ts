import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Cv} from "../model/cv.model";


@Injectable({
  providedIn: 'root'
})
export class CvService {
  private baseUrl = 'https://localhost:8082'; // Promenite URL prema vašem backend serveru

  constructor(private http: HttpClient) {}

  uploadCv(file: File, appUserId: number): Observable<string> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    formData.append('appUserId', appUserId.toString());

    return this.http.post<string>(`${this.baseUrl}/uploadCv`, formData);
  }

  downloadCv(appUserId: number): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/downloadCv/${appUserId}`, {
      responseType: 'blob'
    });
  }

  getAllCvs(): Observable<Cv[]> {
    return this.http.get<Cv[]>(`${this.baseUrl}/cvs`);
  }

  getCvsByProjectManager(projectManagerId: number): Observable<Cv[]> {
    return this.http.get<Cv[]>(`${this.baseUrl}/cvs/projectManager/${projectManagerId}`);
  }
}
