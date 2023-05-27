import {Component, Input, OnInit} from '@angular/core';
import { AppUser } from '../../security/model/appUser.model';
import { AppUserService } from '../../security/service/appUser.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { Project } from '../../security/model/project.model';
import { ProjectService } from '../../security/service/project.service';
import { UserDto } from '../../security/dto/user';

@Component({
  selector: 'app-project-employees',
  templateUrl: './project-employees.component.html',
  styleUrls: ['./project-employees.component.css']
})
export class ProjectEmployeesComponent implements OnInit {
  public user: UserDto = new UserDto();
  @Input() employees :AppUser[]=[]
  displayedColumns: string[] = ['Name','Surname', 'Email'];
  constructor(private appUserService : AppUserService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.appUserService.getById(1).subscribe(res => {
      this.user = res;
    })
  }


}
