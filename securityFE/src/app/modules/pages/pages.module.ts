import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { HomeComponent } from './home/home.component';
import { RegistrationComponent } from './registration/registration.component';
import {FormsModule} from "@angular/forms";
import { ValidateRegistrationComponent } from './validate-registration/validate-registration.component';

@NgModule({
  declarations: [
    HomeComponent,
    RegistrationComponent,
    ValidateRegistrationComponent,
  ],
    imports: [
        CommonModule,
        AppRoutingModule,
        FormsModule,
    ]
})
export class PagesModule { }
