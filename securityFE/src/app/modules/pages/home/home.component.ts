import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import { AuthService } from '../../security/service/auth.service';
import { TokenStorageService } from '../../security/service/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router, private authService: AuthService, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
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
