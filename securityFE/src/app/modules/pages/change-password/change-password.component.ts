import { Component, OnInit } from '@angular/core';
import { AppUser } from '../../security/model/appUser.model';
import { AppUserService } from '../../security/service/appUser.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { Project } from '../../security/model/project.model';
import { ProjectService } from '../../security/service/project.service';
import { UserDto } from '../../security/dto/user';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  public user: UserDto = new UserDto();

  constructor(private appUserService : AppUserService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.appUserService.getById(1).subscribe(res => {
      this.user = res;
    })
  }

  public cancel(): void {
    this.router.navigate(['/profile'])
  }

  private isValidInput(): boolean {
    return this.user?.firstName != '' && this.user?.lastName != ''
      && this.user?.email != '';
  }

  public updateUser(): void {
    if (!this.isValidInput()) return;
    //this.appUserService.updateUser(this.user).subscribe(res => {
    //  this.router.navigate(['/profile']);
    //});
  }

  public changePassword(): void {
    //this.appUserService.updateUser(this.user).subscribe(res => {
    //  alert("Success!");
    //  console.log(this.user)
    //});
  }
}
