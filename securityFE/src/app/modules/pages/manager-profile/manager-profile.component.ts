import { Component, OnInit } from '@angular/core';
import { AppUser } from '../../security/model/appUser.model';
import { AppUserService } from '../../security/service/appUser.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { Project } from '../../security/model/project.model';
import { ProjectService } from '../../security/service/project.service';
import { UserDto } from '../../security/dto/user';

@Component({
  selector: 'app-manager-profile',
  templateUrl: './manager-profile.component.html',
  styleUrls: ['./manager-profile.component.css']
})
export class ManagerProfileComponent implements OnInit {
  public user: UserDto = new UserDto();

  constructor(private appUserService : AppUserService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.appUserService.getById(1).subscribe(res => {
      this.user = res;
    })
  }

  public editProfile(): void {
    this.router.navigate(['/manager-profile/update'])
  }

  public pastProjects(): void {
    this.router.navigate(['/manager-past-projects'])
  }

  public currentProjects(): void {
    this.router.navigate(['/manager-current-projects'])
  }

}
