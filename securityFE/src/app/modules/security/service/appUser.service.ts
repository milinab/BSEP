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


  constructor(private http: HttpClient) { }

  getUsers(): Observable<AppUser[]> {
    return this.http.get<AppUser[]>(this.apiHost + 'api/v1/user', {headers: this.headers});
  }

  getById(id: number): Observable<AppUser> {
    return this.http.get<AppUser>(this.apiHost + 'api/v1/user/' + id, {headers: this.headers});
  }

  // updateUser(user: AppUser, id: number): Observable<AppUser> {
  //   return this.http.put<AppUser>(this.apiHost + 'api/v1/user' + id, user, {headers: this.headers});
  // }

  updateUser(user: UserDto, id: number): Observable<UserDto> {
    const url = `${this.apiHost}api/v1/user/${id}`;
    return this.http.put<AppUser>(url, user, {headers: this.headers});
  }
  
}
