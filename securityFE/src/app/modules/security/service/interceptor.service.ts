import { HttpClient, HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, switchMap, take, throwError } from "rxjs";
import { AuthService } from "./auth.service";

@Injectable()
export class Interceptor implements HttpInterceptor {
    constructor(private http: HttpClient, private authService: AuthService) { }

    baseApiUrl: string = 'https://localhost:8082/api/v1/';

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if(req.url.indexOf(this.baseApiUrl) == -1){
            return next.handle(req);
        }
        return this.authService.accessToken$.pipe(
            take(1),
            switchMap((t: string | null) => {
                let token: string = ''
                token = t ? t : '';

                if (req.headers.has('logoutHeader')) {
                    const request = req.clone({
                        withCredentials: true
                    })

                    return next.handle(request).pipe(catchError((err: HttpErrorResponse) => {
                        return throwError(() => err)
                    }))
                } else {
                    const request = req.clone({
                        setHeaders: {
                            Authorization: `Bearer ${token}`
                        }
                    });

                    return next.handle(request).pipe(catchError((err: HttpErrorResponse) => {
                        if (err.status === 403 && !request.url.includes('authenticate')) {
                            return this.http.post(`${this.baseApiUrl}auth/token/refresh`, {}, { withCredentials: true }).pipe(
                                switchMap((res: any) => {
                                    token = res.accessToken
                                    this.authService.setAccessToken = res.accessToken;

                                    return next.handle(req.clone({
                                        setHeaders: {
                                            Authorization: `Bearer ${token}`
                                        }
                                    }))
                                })
                            )
                        }
                        return throwError(() => err)
                    }))
                }
            })
        );
    }
}