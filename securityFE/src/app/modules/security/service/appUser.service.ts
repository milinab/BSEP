import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import { AppUser } from "../model/appUser.model";
import { Observable } from "rxjs";
import { UserDto } from "../dto/user";


@Injectable({
  providedIn: 'root'
})

export class AppUserService{
  apiHost: string = 'https://localhost:8082/';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  baseApiUrl: string = 'https://localhost:8082/api/v1/user';
  baseUrl: string = 'https://localhost:8082/api/v1/appUser';

  constructor(private https: HttpClient) { }

  getUsers(): Observable<AppUser[]> {
    return this.https.get<AppUser[]>(this.apiHost + 'api/v1/user', {headers: this.headers});
  }

  getById(id: number): Observable<AppUser> {
    return this.https.get<AppUser>(this.apiHost + 'api/v1/user/' + id, {headers: this.headers});
  }

  getUser(id: number, token: string): Observable<AppUser> {
    return this.https.get<AppUser>(this.apiHost + 'api/v1/user' + id, {headers: this.headers});
  }

  updateUser(payload: any): Observable<void> {
    const url = `${this.baseApiUrl}/`+payload.id;
    console.log(payload);
    console.log("URLLL" + url);
    return this.https.put<void>(url, JSON.stringify(payload), { headers: this.headers });
  }

  //updateManager(user: any): Observable<any> {
  //  return this.https.put<any>(this.apiHost + 'api/v1/appUser/updateManager/' + user.id, user,{headers: this.headers});
  //}

  getPendingUsers(): Observable<AppUser[]> {
    const url = `${this.apiHost}api/v1/appUser/pending`;
    return this.https.get<AppUser[]>(url, { headers: this.headers });
  }

  getByEmail(email: string): Observable<AppUser> {
    return this.https.get<AppUser>(this.apiHost + 'api/v1/appUser/' + email, { headers: this.headers });
  }

  getEngineerUsers(): Observable<AppUser[]> {
    const url = `${this.apiHost}api/v1/appUser/engineer`;
    return this.https.get<AppUser[]>(url, { headers: this.headers });
  }

  // searchUsers(searchCriteria: any) {
  //   const url = `${this.baseUrl}/search`;
  //   return this.https.get<AppUser[]>(url, {params: searchCriteria});
  // }
  searchUsers(firstName: string, lastName: string, email: string, startDate: string, endDate: string): Observable<AppUser[]> {
    const params = new HttpParams()
      .set('firstName', firstName || '')
      .set('lastName', lastName || '')
      .set('email', email || '')
      .set('startDateString', startDate || '')
      .set('endDateString', endDate || '')


    return this.https.get<AppUser[]>(`${this.baseUrl}/search`, { params });
  }

}
