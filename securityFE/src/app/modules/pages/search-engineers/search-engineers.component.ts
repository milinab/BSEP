import { Component, OnInit } from '@angular/core';
import { AppUser } from '../../security/model/appUser.model';
import { AppUserService } from '../../security/service/appUser.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-search-engineers',
  templateUrl: './search-engineers.component.html',
  styleUrls: ['./search-engineers.component.css']
})
export class SearchEngineersComponent implements OnInit {

  public user: AppUser = new AppUser();
  public allEngineers: AppUser[] = [];
  public matchingUsers: AppUser[] = [];
  //public startDate: string = '';

  constructor(private appUserService : AppUserService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getEngineerUsers();
  }

  getEngineerUsers(): void {
    this.appUserService.getEngineerUsers().subscribe(users => {
      this.allEngineers = users;
    });
  }

  searchUsers(): void {
   // const startDateString: string | null = this.startDate !== '' ? new Date(this.startDate).toISOString() : null;
    this.appUserService.searchUsers(this.user.firstName, this.user.lastName, this.user.email, this.user.startDate)
      .subscribe(matchingUsers => {
        this.matchingUsers = matchingUsers;
      });
  }

}
