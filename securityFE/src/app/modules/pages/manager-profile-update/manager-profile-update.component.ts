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

  constructor(private appUserService : AppUserService, private tokenStorageService: TokenStorageService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.appUserService.getById(1).subscribe(res => {
      this.user = res;
    })
    const loggedUser = this.tokenStorageService.getUser();
    console.log("ovo je user:");
    console.log(loggedUser);
    this.appUserService.getByEmail(loggedUser.sub).subscribe(res => {
      this.appUser = res;
      console.log("GETOVAN USER:", this.appUser);
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
    if (!this.isValidInput()) return;
    //this.appUserService.updateUser(this.user).subscribe(res => {
    //  this.router.navigate(['/manager-profile']);
    //});
  }

  public changePassword(): void {
    this.router.navigate(['change-password'])
  }
}
