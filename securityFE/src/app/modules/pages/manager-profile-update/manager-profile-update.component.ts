import { Component, OnInit } from '@angular/core';
import { AppUser } from '../../security/model/appUser.model';
import { AppUserService } from '../../security/service/appUser.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { Project } from '../../security/model/project.model';
import { ProjectService } from '../../security/service/project.service';
import { UserDto } from '../../security/dto/user';
import {TokenStorageService} from "../../security/service/token-storage.service";

@Component({
  selector: 'app-manager-profile-update',
  templateUrl: './manager-profile-update.component.html',
  styleUrls: ['./manager-profile-update.component.css']
})
export class ManagerProfileUpdateComponent implements OnInit {
  public user: UserDto = new UserDto();
  appUser: AppUser | undefined;
  public newPassword = '';

  constructor(private appUserService : AppUserService, private tokenStorageService: TokenStorageService, private router: Router, private route: ActivatedRoute) { }

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
      this.user.password = this.appUser.password;
      this.user.appUserRole = this.appUser.appUserRole
    });
  }

  public cancel(): void {
    this.router.navigate(['/manager-profile'])
  }



  private isValidInput(): boolean {
    return this.user?.firstName != '' && this.user?.lastName != ''
      && this.user?.email != '';
  }

  public updateUser(): void {
    this.appUserService.updateUser(this.user).subscribe(
      data => {
        console.log(data);
        this.router.navigate(['manager-profile'])
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
