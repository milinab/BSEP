import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {BehaviorSubject, EMPTY, Observable, catchError, tap} from "rxjs";
import { Router } from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  apiHost: string = 'https://localhost:8082/';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  private accessTokenSubject$ = new BehaviorSubject<string | null>(null);
  public accessToken$ = this.accessTokenSubject$.asObservable();

  set setAccessToken(token: string | null) {
    this.accessTokenSubject$.next(token);
  }
  clearAccessToken(): void {
    this.accessTokenSubject$.next(null);
  }

  constructor(private http: HttpClient, private router: Router) { }

  register(registerRequest: any): Observable<any> {
    return this.http.post<any>(this.apiHost + 'api/v1/registration/pending', registerRequest, {headers: this.headers});
  }

  registerr(registerRequest: any): Observable<any> {
    return this.http.post<any>(this.apiHost + 'api/v1/registration/register', registerRequest, {headers: this.headers});
  }

  authenticate(loginRequest: any): Observable<any> {
    let body = new URLSearchParams()
    body.set("email", loginRequest.email)
    body.set("password", loginRequest.password)
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    })
    const options = {
      headers: headers,
      withCredentials: true
    }
    return this.http.post<any>(this.apiHost + 'api/v1/auth/authenticate', body, options).pipe(
      tap((res: any) => this.setAccessToken = res.accessToken)
    );
  }

  logout(): Observable<any> {
    return this.http.post(this.apiHost + `api/v1/auth/logout`, '', { withCredentials: true}).pipe(
      catchError(_ => EMPTY),
      tap(_ => {
        this.clearAccessToken()
        console.log("redirect")
        this.router.navigate(['/home']);
      })
    );
  }

  denyRegistration(userId: number, denialReason: string): Observable<any> {
    const url = `${this.apiHost}api/v1/registration/${userId}/deny`;
    const params = new HttpParams().set('reason', denialReason);
    const options = { headers: this.headers, params: params };
    return this.http.post<any>(url, null, options);
  }

}