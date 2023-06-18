import { Component, OnInit } from '@angular/core';
import {AppUser} from "../../security/model/appUser.model";
import {CvService} from "../../security/service/cv.service";
import {TokenStorageService} from "../../security/service/token-storage.service";
import {UserToken} from "../../security/model/userToken.model";
import {AppUserService} from "../../security/service/appUser.service";

@Component({
  selector: 'app-cv-engineers',
  templateUrl: './cv-engineers.component.html',
  styleUrls: ['./cv-engineers.component.css']
})
export class CvEngineersComponent implements OnInit {
  engineerUsers: AppUser[] | undefined;
  private loggedUser: any;

  constructor(private cvService: CvService, private tokenStorageService: TokenStorageService, private appUserService: AppUserService) { }

  ngOnInit(): void {
    const loggedUser = this.tokenStorageService.getUser();
    this.getEngineerUsers(parseInt(loggedUser.id));
  }

  getEngineerUsers(projectManagerId: number): void {
    this.cvService.getCvsByProjectManager(projectManagerId).subscribe(cvs => {
      this.engineerUsers = cvs.map(cv => cv.appUser);
    });
  }

  showCv(appUserId: number): void {
    this.cvService.downloadCv(appUserId).subscribe(data => {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const cvContent = e.target.result;
        window.open(cvContent, '_blank');
      };
      reader.readAsDataURL(data);
    });
  }
}
