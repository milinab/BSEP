import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { UserDto } from "../../security/dto/user";
import { UserToken } from "../../security/model/userToken.model";
import { AppUser } from "../../security/model/appUser.model";
import { AppUserService } from "../../security/service/appUser.service";
import { ActivatedRoute, Router } from "@angular/router";
import { TokenStorageService } from "../../security/service/token-storage.service";
import { CvService } from "../../security/service/cv.service";
import { Work } from "../../security/model/work.model";
import { WorkService } from "../../security/service/work.service";

@Component({
  selector: 'app-engineer-profile',
  templateUrl: './engineer-profile.component.html',
  styleUrls: ['./engineer-profile.component.css']
})
export class EngineerProfileComponent implements OnInit {
  @ViewChild('fileInput') fileInput: ElementRef | undefined;
  works: Work[] = [];
  public user: UserDto = new UserDto();
  public userToken: UserToken = new UserToken("", "", 0);
  appUser: AppUser | undefined;
  public newPassword = '';

  constructor(
    private workService: WorkService,
    private cvService: CvService,
    private appUserService: AppUserService,
    private router: Router,
    private route: ActivatedRoute,
    private tokenStorageService: TokenStorageService
  ) {}

  ngOnInit(): void {
    var loggedUser = this.tokenStorageService.getUser();
    console.log("loggedUser ", loggedUser);
    this.appUserService.getByEmail(loggedUser.sub).subscribe(res => {
      this.appUser = res;
      console.log("GETOVAN USER:", this.appUser);

      this.user.id = this.appUser.id;
      this.user.email = this.appUser.email;
      this.user.firstName = this.appUser.firstName;
      this.user.lastName = this.appUser.lastName;
      this.user.appUserRole = this.appUser.appUserRole;

      this.getWorksByWorkerId(this.user.id);
    });
  }

  updateUser(): void {
    console.log("UPDATE USER COMPONENT");
    console.log(this.user);
    this.appUserService.updateUser(this.user).subscribe(
      data => {
        console.log(data);
        window.location.reload();
      },
      error => {
        console.error('Failed to update user:', error);
      }
    );
  }

  uploadCv(): void {
    if (this.fileInput && this.fileInput.nativeElement.files && this.fileInput.nativeElement.files.length > 0) {
      const file = this.fileInput.nativeElement.files[0];
      this.cvService.uploadCv(file, this.user.id).subscribe(
        (data) => {
          console.log('CV uploaded successfully:', data);
          // Handle success as needed
        },
        (error) => {
          console.error('Failed to upload CV:', error);
          // Handle error as needed
        }
      );
    }
  }

  getWorksByWorkerId(workerId: number): void {
    this.workService.getWorksByWorkerId(workerId).subscribe(
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

  updatePassword(){
    console.log("MEW PASS")
      console.log(this.newPassword);
      console.log(this.user);
      this.appUserService.updatePassword({email:this.user.email,
        editedPassword: this.newPassword}).subscribe(data =>
        console.log("NOVA: ",data));
        alert("password has been changed");
    }
  }

