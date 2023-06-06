import { Component, OnInit } from '@angular/core';
import { AppUser } from '../../security/model/appUser.model';
import { Router } from '@angular/router';
import { AppUserService } from '../../security/service/appUser.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  public users: AppUser[] = [];

  constructor(private userService: AppUserService, private router: Router) { }

  ngOnInit(): void {
    this.userService.getUsers().subscribe(res => {
      this.users = res;
      this.users = this.users.filter(w => {
        let isAdmin = false;  
        if (w.appUserRole === 'ADMIN') isAdmin = true;
        return !isAdmin;
      });
    })
  }


}
