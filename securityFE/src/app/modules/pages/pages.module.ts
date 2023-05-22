import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { HomeComponent } from './home/home.component';
import { RegistrationComponent } from './registration/registration.component';
import {FormsModule} from "@angular/forms";
import { MatTableModule } from '@angular/material/table';
import { AdminProfileComponent } from './admin-profile/admin-profile.component';
import { ProjectsComponent } from './projects/projects.component';
import { UsersComponent } from './users/users.component';
import { ValidateRegistrationComponent } from './validate-registration/validate-registration.component';

@NgModule({
  declarations: [
    HomeComponent,
    RegistrationComponent,
    AdminProfileComponent,
    ProjectsComponent,
    UsersComponent,
    ValidateRegistrationComponent
  ],
    imports: [
        CommonModule,
        AppRoutingModule,
        FormsModule,
        MatTableModule
    ]
})
export class PagesModule { }
