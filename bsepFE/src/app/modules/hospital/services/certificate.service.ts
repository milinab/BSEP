import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {CertificateModel} from "../model/certificate.model";
import {Room} from "../model/room.model";
import {CertificateIssuerDTOModel} from "../model/certificateIssuerDTO.model";
import {CertificateStatusDTOModel} from "../model/certificateStatusDTO.model";

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  apiHost: string = 'http://localhost:8082/';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  private alias: string | undefined;

  constructor(private http: HttpClient) { }


  revokeCertificate(alias: string): Observable<any>{
    return this.http.put<any>(this.apiHost + 'api/certificate/revoke/' + alias, {headers: this.headers});
  }

  createCertificate(certificate: any): Observable<any> {
    return this.http.post<any>(this.apiHost + 'api/certificate/create', certificate, {headers: this.headers});
  }

  getRootCerts(): Observable<String> {
    return this.http.get<String>(this.apiHost + 'api/certificate/root-certs', {headers: this.headers});
  }

  getIntermediaryCerts(): Observable<String> {
    return this.http.get<String>(this.apiHost + 'api/certificate/intermediary-certs', {headers: this.headers});
  }

  getEndEntityCerts(): Observable<String> {
    return this.http.get<String>(this.apiHost + 'api/certificate/end-entity-certs', {headers: this.headers});
  }

  getIssues(): Observable<CertificateIssuerDTOModel[]> {
    return this.http.get<CertificateIssuerDTOModel[]>(this.apiHost + 'api/certificate/allCertificateIssuers', {headers: this.headers});
  }

  getIssueByAlias(alias: string): Observable<CertificateIssuerDTOModel>{
    return this.http.get<CertificateIssuerDTOModel>(this.apiHost + 'api/certificate/issues/'+alias, {headers: this.headers});
  }

  getCertificateStatusByAlias(alias: string | undefined): Observable<CertificateStatusDTOModel>{
    return this.http.get<CertificateStatusDTOModel>(this.apiHost + 'api/certificate/certificateStatus/'+alias, {headers: this.headers});
  }

  uploadFile(file: File) {
    const formData = new FormData();
    formData.append('file', file);

    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'multipart/form-data'
      })
    };

    return this.http.post(this.apiHost + '/upload', formData, options);
  }

  downloadCertificate(certificateId: string): Observable<Blob> {
    const options = {
      responseType: 'blob' as const, // Set response type as blob
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.get(this.apiHost + 'api/certificate/download/' + certificateId, {
      headers: this.headers,
      responseType: 'arraybuffer'
    }).pipe(map((data: ArrayBuffer) => {
      return new Blob([data], { type: 'application/octet-stream' });
    }));
  }


}
