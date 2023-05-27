import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { AppUser } from "../model/appUser.model";
import { Observable } from "rxjs";
import { UserDto } from "../dto/user";


@Injectable({
  providedIn: 'root'
})

export class AppUserService{
  apiHost: string = 'http://localhost:8082/';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  baseApiUrl: string = 'http://localhost:8082/api/v1/user';

  constructor(private http: HttpClient) { }

  getUsers(): Observable<AppUser[]> {
    return this.http.get<AppUser[]>(this.apiHost + 'api/v1/user', {headers: this.headers});
  }

  getById(id: number): Observable<AppUser> {
    return this.http.get<AppUser>(this.apiHost + 'api/v1/user/' + id, {headers: this.headers});
  }

  getUser(id: number, token: string): Observable<AppUser> {
    return this.http.get<AppUser>(this.apiHost + 'api/v1/user' + id, {headers: this.headers});
  }

  updateUser(payload: any): Observable<void> {
    const url = `${this.baseApiUrl}/`+payload.id; 
    console.log(payload);
    console.log("URLLL" + url);
    return this.http.put<void>(url, JSON.stringify(payload), { headers: this.headers });
  }
  
  
  getPendingUsers(): Observable<AppUser[]> {
    const url = `${this.apiHost}api/v1/appUser/pending`;
    return this.http.get<AppUser[]>(url, { headers: this.headers });
  }

  getByEmail(email: string): Observable<AppUser> {
    return this.http.get<AppUser>(this.apiHost + 'api/v1/appUser/' + email, { headers: this.headers });
  }

}
