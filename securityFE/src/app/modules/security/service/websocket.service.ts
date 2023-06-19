import { Injectable } from '@angular/core';
import { WebSocketSubject } from 'rxjs/webSocket';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private socket$: WebSocketSubject<any> ;

  constructor() {
    this.socket$ = new WebSocketSubject<any>('wss://localhost:8082/socket');
  }

  public connect(url: string): void {
    this.socket$ = new WebSocketSubject(url);
  }

  public subscribeToChannel(channel: string): void {
    this.socket$.next({ type: 'subscribe', channel: channel });
  }

  public unsubscribeFromChannel(channel: string): void {
    this.socket$.next({ type: 'unsubscribe', channel: channel });
  }

  public onMessage(): Observable<any> {
    return this.socket$.asObservable();
  }

  public disconnect(): void {
    this.socket$.complete();
  }
}
