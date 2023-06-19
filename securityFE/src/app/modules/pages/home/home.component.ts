import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import { AuthService } from '../../security/service/auth.service';
import { TokenStorageService } from '../../security/service/token-storage.service';
import {WebSocketService} from "../../security/service/websocket.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  notifications: any[] = [];

  constructor(private router: Router,
              private authService: AuthService,
              private tokenStorageService: TokenStorageService,
              private websocketService: WebSocketService,
              private http: HttpClient) { }

  ngOnInit(): void {
    this.websocketService.connect('wss://localhost:8082/socket');
    this.websocketService.onMessage().subscribe(message => {
      this.showNotification(message);
    });
  }

  private showNotification(message: any): void {
    const notification = { text: message.text };
    this.notifications.push(notification);

    setTimeout(() => {
      this.removeNotification(notification);
    }, 8000);
  }

  private removeNotification(notification: any): void {
    const index = this.notifications.indexOf(notification);
    if (index !== -1) {
      this.notifications.splice(index, 1);
    }
  }

  ngOnDestroy(): void {
    this.websocketService.disconnect();
  }

  public profile(): void {
    this.router.navigate(['/manager-profile'])
  }

  public signOut(): void {
    this.authService.logout().subscribe(() => {
      this.tokenStorageService.removeToken();
      this.tokenStorageService.removeUser();
      this.router.navigate(['/login']);
    });
  }
}
