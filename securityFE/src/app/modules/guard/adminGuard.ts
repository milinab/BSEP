import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { TokenStorageService } from '../security/service/token-storage.service';
import { AppUserService } from '../security/service/appUser.service';
import { UserDto } from '../security/dto/user';
import { AppUser } from '../security/model/appUser.model';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  public user: UserDto = new UserDto();
  appUser: AppUser | undefined;

  constructor(
    private tokenStorageService: TokenStorageService,
    private appUserService: AppUserService,
    private router: Router
  ) { }

  canActivate(): Promise<boolean> {
    const loggedUser = this.tokenStorageService.getUser();
    console.log("user-email:", loggedUser);

    return new Promise<boolean>((resolve, reject) => {
      this.appUserService.getByEmail(loggedUser.sub).subscribe(res => {
        this.appUser = res;

        if (this.appUser?.appUserRole === "ADMIN") {
          // Korisnik je admin, dozvoli pristup
          resolve(true);
        } else {
          // Korisnik nije admin, ne dozvoli pristup
          resolve(false);
          // Preusmeri korisnika na odgovarajuću stranicu
          this.router.navigate(['/forbidden-page']);
        }
      }, error => {
        // Handle any errors that occur during the API call
        reject(error);
      });
    });
  }
}
