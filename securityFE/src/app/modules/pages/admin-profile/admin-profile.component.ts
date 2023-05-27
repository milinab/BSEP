import { Component, OnInit } from '@angular/core';
import { AppUser } from '../../security/model/appUser.model';
import { AppUserService } from '../../security/service/appUser.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { Project } from '../../security/model/project.model';
import { ProjectService } from '../../security/service/project.service';
import { UserDto } from '../../security/dto/user';
import { TokenStorageService } from '../../security/service/token-storage.service';
import { UserToken } from '../../security/model/userToken.model';

@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.css']
})
export class AdminProfileComponent implements OnInit {
  public user: UserDto = new UserDto();
  public userToken: UserToken = new UserToken("","",0);
  appUser: AppUser | undefined;

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
      this.user.password = this.appUser.password;
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
  
  
}