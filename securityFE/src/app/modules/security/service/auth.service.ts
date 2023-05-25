import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  apiHost: string = 'http://localhost:8082/';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) { }

  register(registerRequest: any): Observable<any> {
    return this.http.post<any>(this.apiHost + 'api/v1/registration', registerRequest, {headers: this.headers});
  }

  authenticate(loginRequest: any): Observable<any> {
    return this.http.post<any>(this.apiHost + 'api/v1/auth/authenticate', loginRequest, {headers: this.headers});
  }

}
