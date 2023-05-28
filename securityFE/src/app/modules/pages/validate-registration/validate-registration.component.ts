import { Component, OnInit } from '@angular/core';
import { AppUser } from "../../security/model/appUser.model";
import { AppUserService } from "../../security/service/appUser.service";
import { catchError } from "rxjs/operators";
import { AuthService } from "../../security/service/auth.service";

@Component({
  selector: 'app-validate-registration',
  templateUrl: './validate-registration.component.html',
  styleUrls: ['./validate-registration.component.css']
})
export class ValidateRegistrationComponent implements OnInit {

  users: AppUser[] = [];
  user: AppUser = new AppUser(); // Create an instance of AppUser to store the registration details

  constructor(private appUserService: AppUserService, private authService: AuthService) { }

  ngOnInit(): void {
    this.appUserService.getPendingUsers().subscribe(
      (users: AppUser[]) => {
        this.users = users;
      },
      (error: any) => {
        console.error('Error fetching pending users:', error);
      }
    );
  }

  registerUser(user: AppUser) {
    this.authService.registerr(user).subscribe(
      () => {
        alert('Registered successfully');
        // Reset the user object after successful registration
        this.user = new AppUser();
      },
      (error: any) => {
       // alert('Error during registration');
       // console.error('Registration error:', error);
      }
    );
  }


  denyRegistration(userId: number, denialReason: string) {
    this.authService.denyRegistration(userId, denialReason).subscribe(
      () => {
        alert('Registration denied');
        // Optionally, you can refresh the list of pending users after denying registration
        this.appUserService.getPendingUsers().subscribe(
          (users: AppUser[]) => {
            this.users = users;
          },
          (error: any) => {
            console.error('Error fetching pending users:', error);
          }
        );
      },
      (error: any) => {
        alert('Error denying registration');
        console.error('Deny registration error:', error);
      }
    );
  }
  }
