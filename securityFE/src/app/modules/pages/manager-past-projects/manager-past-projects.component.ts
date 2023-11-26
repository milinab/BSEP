import {Component, Input, OnInit} from '@angular/core';
import { AppUser } from '../../security/model/appUser.model';
import { AppUserService } from '../../security/service/appUser.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { Project } from '../../security/model/project.model';
import { ProjectService } from '../../security/service/project.service';
import { UserDto } from '../../security/dto/user';
import {Work} from "../../security/model/work.model";
import {WorkService} from "../../security/service/work.service";
import {TokenStorageService} from "../../security/service/token-storage.service";

@Component({
  selector: 'app-manager-past-projects',
  templateUrl: './manager-past-projects.component.html',
  styleUrls: ['./manager-past-projects.component.css']
})
export class ManagerPastProjectsComponent implements OnInit {
  public user: UserDto = new UserDto();
  @Input() projects :Project[]=[]
  works: Work[] = [];
  appUser: AppUser | undefined;

  constructor(private workService: WorkService, private tokenStorageService: TokenStorageService, private appUserService : AppUserService, private router: Router, private route: ActivatedRoute) { }

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

      this.getWorksByWorkerId(this.user.id);
    });
  }

  public cancel(): void {
    this.router.navigate(['/manager-profile'])
  }

  public back(): void {
    this.router.navigate(['/manager-profile'])
  }

  private isValidInput(): boolean {
    return this.user?.firstName != '' && this.user?.lastName != ''
      && this.user?.email != '';
  }

  getWorksByWorkerId(workerId: number): void {
    this.workService.getPastWorksByWorkerId(workerId).subscribe(
      (works) => {
        this.works = works;
        // Handle success as needed
      },
      (error) => {
        console.error('Failed to fetch works:', error);
        // Handle error as needed
      }
    );
  }

  public engineers(id: number): void {
    console.log("project id:",id);
    this.router.navigate(['/project-employees/' + id]);
  }
}
