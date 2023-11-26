import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AuthService } from '../../security/service/auth.service';
import { Router } from '@angular/router';
import { LoginRequest } from '../../security/dto/loginRequest.model';
import { TokenStorageService } from '../../security/service/token-storage.service';
import { AppUserService } from '../../security/service/appUser.service';
import { UserDto } from '../../security/dto/user';
import { timeInterval, timeout } from 'rxjs';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public user: UserDto = new UserDto();
  public code: string = '';
  public enterCode: boolean = false;

  loginForm = new FormGroup({
    email: new FormControl<string | undefined>(undefined),
    password: new FormControl<string | undefined>(undefined)
  })

  private authenticated = localStorage.getItem('TOKEN_KEY') ? true : false;

  constructor(private authService: AuthService, private tokenStorageService:TokenStorageService, private router:Router,
     private appUserService: AppUserService, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
  }

  public signIn() {
    const loginRequest:LoginRequest = new LoginRequest({
      email: this.loginForm.controls.email.value!,
      password: this.loginForm.controls.password.value!,
    })
    this.authService.authenticate(loginRequest).subscribe({
        next: response => {
          console.log("X")
          console.log(response)
          this.tokenStorageService.saveToken(response.accessToken)
          this.tokenStorageService.saveUser(response.accessToken)
          const test = window.sessionStorage.getItem('TOKEN_KEY')
          alert("Success!");
          this.enterCode = true;

          console.log(test);
          // this.router.navigate(['/home']).then(
          //   ()=>{
          //     window.location.reload();
          //   }
          //);
        },
        error: message => {
          console.log(message.error.errorMessage)
          alert(message.error.errorMessage)
        }
      }
    )
  }

  recoverPassword() {
    const email = this.loginForm.value.email;
    console.log("preuzeti email: " + email);
    
    if (email) {
      this.appUserService.recoverAccount(email).subscribe(
        () => {
          alert('Account recovery email sent successfully!');
        },
        (error) => {
          alert('Account recovery failed. Please try again later.');
          console.error(error);
        }
      );
    } else {
      alert('Please enter a valid email address.');
    }
  }

  submitCode(){
    this.authService.submitCode(this.code).subscribe(res =>{
      if(res)
      this.router.navigate(['/home'])
      else
      alert("Invalid 2FA code!")
    })
  }

}
