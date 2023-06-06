import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AttachmentService{
  apiHost: string = 'https://localhost:8082/';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private https: HttpClient) { }

  uploadFile(file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file);

    return this.https.post<any>(this.apiHost + 'upload', formData);
  }

  downloadFileByAppUserId(appUserId: number): Observable<any> {
    return this.https.get(`${this.apiHost}download/appUser/${appUserId}`, { responseType: 'blob' });
  }
}
