import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {CertificateModel} from "../model/certificate.model";
import {Room} from "../model/room.model";
import {CertificateIssuerDTOModel} from "../model/certificateIssuerDTO.model";

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  apiHost: string = 'http://localhost:8082/';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) { }


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

}
