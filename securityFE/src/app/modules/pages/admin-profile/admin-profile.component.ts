import { Component, OnInit } from '@angular/core';
import { AppUser } from '../../security/model/appUser.model';
import { AppUserService } from '../../security/service/appUser.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { Project } from '../../security/model/project.model';
import { ProjectService } from '../../security/service/project.service';
import { UserDto } from '../../security/dto/user';

@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.css']
})
export class AdminProfileComponent implements OnInit {
  public user: UserDto = new UserDto();

 
  constructor(private appUserService : AppUserService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.appUserService.getById(1).subscribe(res => {
    this.user = res;
    })
  }

  // updateUser(): void {
  //   this.appUserService.updateUser(this.user, this.user.id).subscribe(
  //     (updatedUser: UserDto) => {
  //       this.user = updatedUser;
  //       console.log('User updated successfully:', updatedUser);
  //     },
  //     (error: any) => {
  //       console.error('Error updating user:', error);
  //     }
  //   );
  // }
}