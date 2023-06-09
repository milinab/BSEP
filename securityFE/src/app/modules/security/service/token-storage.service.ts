import { Injectable } from '@angular/core';
import { UserToken } from '../model/userToken.model';
import { Observable } from 'rxjs';
import { AppUser } from '../model/appUser.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  appUser: AppUser | undefined;
  constructor(private https: HttpClient) { }
  apiHost: string = 'https://localhost:8082/';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });



  signOut(): void {
    window.sessionStorage.clear();
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.removeItem(USER_KEY);
  }
  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
    console.log(token)
    console.log("saveToken")
  }
  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }
  public isLoggedIn(): boolean {
    return !!window.sessionStorage.getItem(TOKEN_KEY);
  }
  public saveUser(token: string): void {
    let user: string = atob(token.split('.')[1]);
    console.log(user)
    let userObject = JSON.parse(user)
    console.log("userObject", userObject);



    let userTk: UserToken = new UserToken(userObject.sub, userObject.id, userObject.role);
    console.log("userTk", userTk)

    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(userTk));


  }
  public getUser(): UserToken {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      console.log(window.sessionStorage.getItem(USER_KEY))
      return JSON.parse(user);
    }
    return new UserToken("", "", 0);
  }

}
