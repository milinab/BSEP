import { Component, OnInit } from '@angular/core';
import {AppUserService} from "../../security/service/appUser.service";
import {AppUser} from "../../security/model/appUser.model";

@Component({
  selector: 'app-engineers-cv',
  templateUrl: './engineers-cv.component.html',
  styleUrls: ['./engineers-cv.component.css']
})
export class EngineersCvComponent implements OnInit {
  engineerUsers: AppUser[] | undefined;

  constructor(private appUserService: AppUserService) { }

  ngOnInit(): void {
    this.getEngineerUsers();
  }

  getEngineerUsers(): void {
    this.appUserService.getEngineerUsers().subscribe(users => {
      this.engineerUsers = users;
    });
  }
}


