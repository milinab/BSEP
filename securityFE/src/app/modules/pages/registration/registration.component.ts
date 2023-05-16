import { Component, OnInit } from '@angular/core';
import { AppUserService } from '../../security/service/appUser.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from '../../security/service/auth.service';
import { AppUser } from '../../security/model/appUser.model';
import { catchError } from 'rxjs/operators';



@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
  providers: [MatDialog]
})
export class RegistrationComponent implements OnInit {

  public user: AppUser = new AppUser();

  constructor(
    public dialog: MatDialog,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.authService.register(this.user).subscribe(
      res => {
        alert('Registered successfully');
        this.router.navigate(['']);
      },
      error => {
        catchError(error);
        alert(error);
      }
    );
  }
}
