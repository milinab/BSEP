import {Component, Input, OnInit} from '@angular/core';
import { AppUser } from '../../security/model/appUser.model';
import { AppUserService } from '../../security/service/appUser.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { Project } from '../../security/model/project.model';
import { ProjectService } from '../../security/service/project.service';
import { UserDto } from '../../security/dto/user';
import {WorkService} from "../../security/service/work.service";
import { Location } from '@angular/common';

@Component({
  selector: 'app-project-employees',
  templateUrl: './project-employees.component.html',
  styleUrls: ['./project-employees.component.css']
})
export class ProjectEmployeesComponent implements OnInit {
  public user: UserDto = new UserDto();
  @Input() workers :AppUser[]=[]
  displayedColumns: string[] = ['Name','Surname', 'Email'];
  constructor(private workService : WorkService, private appUserService : AppUserService, private router: Router, private route: ActivatedRoute, private location: Location) { }

  ngOnInit(): void {
    this.initEmployees();

  }

  public initEmployees(): void {
    this.route.params.subscribe((params: Params) => {
      this.workService.getAllWorkersByProject(params['id']).subscribe(res => {
        this.workers = res;
        console.log("workers:", this.workers)
      })
    });
  }

  public back(): void {
    this.location.back();
  }

  public add(): void {
    this.location.back();
  }

  public dismiss(id: number): void {
    //this.workService.dismissWorker(id).subscribe( res =>
    //{
    //  this.initEmployees();
    //  alert("Success!")
    //});
    this.location.back();
  }
}
