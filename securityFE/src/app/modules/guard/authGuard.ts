import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { TokenStorageService } from '../security/service/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private tokenStorageService: TokenStorageService, private router: Router) {}

  canActivate(): boolean {
    if (this.tokenStorageService.getToken()) {
      // Korisnik je ulogovan, dozvoli pristup
      return true;
    }

    // Korisnik nije ulogovan, preusmeri ga na stranicu za prijavu
    this.router.navigate(['/login']);
    return false;
  }
}
