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
import {ManagerProfileComponent} from "./manager-profile/manager-profile.component";
import {MatDividerModule} from "@angular/material/divider";
import {MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import {ManagerProfileUpdateComponent} from "./manager-profile-update/manager-profile-update.component";
import {MatFormFieldModule} from "@angular/material/form-field";
import {ChangePasswordComponent} from "./change-password/change-password.component";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {ManagerPastProjectsComponent} from "./manager-past-projects/manager-past-projects.component";
import {ManagerCurrentProjectsComponent} from "./manager-current-projects/manager-current-projects.component";
import {ProjectEmployeesComponent} from "./project-employees/project-employees.component";

@NgModule({
  declarations: [
    HomeComponent,
    RegistrationComponent,
    AdminProfileComponent,
    ProjectsComponent,
    UsersComponent,
    ValidateRegistrationComponent,
    ManagerProfileComponent,
    ManagerProfileUpdateComponent,
    ChangePasswordComponent,
    ManagerPastProjectsComponent,
    ManagerCurrentProjectsComponent,
    ProjectEmployeesComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    FormsModule,
    MatTableModule,
    MatDividerModule,
    MatCardModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ]
})
export class PagesModule { }
