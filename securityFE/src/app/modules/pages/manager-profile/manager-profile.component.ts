import { Component, OnInit } from '@angular/core';
import { AppUser } from '../../security/model/appUser.model';
import { AppUserService } from '../../security/service/appUser.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { Project } from '../../security/model/project.model';
import { ProjectService } from '../../security/service/project.service';
import { UserDto } from '../../security/dto/user';
import {UserToken} from "../../security/model/userToken.model";
import {TokenStorageService} from "../../security/service/token-storage.service";

@Component({
  selector: 'app-manager-profile',
  templateUrl: './manager-profile.component.html',
  styleUrls: ['./manager-profile.component.css']
})
export class ManagerProfileComponent implements OnInit {
  public user: UserDto = new UserDto();
  public userToken: UserToken = new UserToken("","",0);
  appUser: AppUser | undefined;

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

  public editProfile(): void {
    this.router.navigate(['/manager-profile/update'])
  }

  public back(): void {
    this.router.navigate(['/home'])
  }

  public pastProjects(): void {
    this.router.navigate(['/manager-past-projects'])
  }

  public currentProjects(): void {
    this.router.navigate(['/manager-current-projects'])
  }

}
