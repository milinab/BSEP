import { Component, OnInit } from '@angular/core';
import { UserDto } from '../../security/dto/user';
import { UserToken } from '../../security/model/userToken.model';
import { AppUser } from '../../security/model/appUser.model';
import { AppUserService } from '../../security/service/appUser.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from '../../security/service/token-storage.service';

@Component({
  selector: 'app-hr-profile',
  templateUrl: './hr-profile.component.html',
  styleUrls: ['./hr-profile.component.css']
})
export class HrProfileComponent implements OnInit {

  public user: UserDto = new UserDto();
  public userToken: UserToken = new UserToken("","",0);
  appUser: AppUser | undefined;
  public newPassword = '';

  constructor(private appUserService : AppUserService, private router: Router, private route: ActivatedRoute, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    var loggedUser = this.tokenStorageService.getUser();
    console.log("loggedUser ", loggedUser)
    this.appUserService.getByEmail(loggedUser.sub).subscribe(res => {
      this.appUser = res;
      console.log("GETOVAN USER:", this.appUser)

      this.user.id = this.appUser.id;
      this.user.email = this.appUser.email;
      this.user.firstName = this.appUser.firstName;
      this.user.lastName = this.appUser.lastName;
      this.user.appUserRole = this.appUser.appUserRole
    });

  }

  updateUser(): void {
    console.log("UPDATE USER COMPONENT");
    console.log(this.user);
    this.appUserService.updateUser(this.user).subscribe(
      data => {
        console.log(data);
        window.location.reload();
      },
      error => {
        console.error('Failed to update user:', error);
      }
    );
  }

  updatePassword(){
    console.log("MEW PASS")
      console.log(this.newPassword);
      console.log(this.user);
      this.appUserService.updatePassword({email:this.user.email,
        editedPassword: this.newPassword}).subscribe(data =>
        console.log("NOVA: ",data));
        alert("password has been changed");
    }

}
