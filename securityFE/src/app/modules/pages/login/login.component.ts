import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AuthService } from '../../security/service/auth.service';
import { Router } from '@angular/router';
import { LoginRequest } from '../../security/dto/loginRequest.model';
import { TokenStorageService } from '../../security/service/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {


  loginForm = new FormGroup({
    email: new FormControl<string | undefined>(undefined),
    password: new FormControl<string | undefined>(undefined)
  })

  private authenticated = localStorage.getItem('TOKEN_KEY') ? true : false;

  constructor(private authService: AuthService, private tokenStorageService:TokenStorageService, private router:Router) { }

  ngOnInit(): void {
  }

  public signIn() {
    const loginRequest:LoginRequest = new LoginRequest({
      email: this.loginForm.controls.email.value!,
      password: this.loginForm.controls.password.value!,
    })
    console.log(loginRequest);
    this.authService.authenticate(loginRequest).subscribe({
        next: response => {
          console.log("X")
          console.log(response)
          this.tokenStorageService.saveToken(response.token)
          this.tokenStorageService.saveUser(response.token)
          const test = window.sessionStorage.getItem('TOKEN_KEY')
          alert("Success!");
          console.log(test);
          this.router.navigate(['/home']).then(
            ()=>{
              window.location.reload();
            }
          );
        },
        error: message => {
          console.log(message.Error)
          alert("Boom!Error!")
        }

      }
    )
  }

}
