import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { HomeComponent } from './home/home.component';
import { RegistrationComponent } from './registration/registration.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
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
import { LoginComponent } from './login/login.component';
import { AllWorkersByProjectComponent } from './all-workers-by-project/all-workers-by-project.component';
import { EngineerProfileComponent } from './engineer-profile/engineer-profile.component';
import {ForbiddenPageComponent} from "./forbidden-page/forbidden-page.component";
import {RbacComponent} from "./rbac/rbac.component";
import { EngineersCvComponent } from './engineers-cv/engineers-cv.component';
import { SearchEngineersComponent } from './search-engineers/search-engineers.component';
import { HrProfileComponent } from './hr-profile/hr-profile.component';
import { CvEngineersComponent } from './cv-engineers/cv-engineers.component';

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
    ProjectEmployeesComponent,
    LoginComponent,
    AllWorkersByProjectComponent,
    EngineerProfileComponent,
    ForbiddenPageComponent,
    RbacComponent,
    EngineersCvComponent,
    SearchEngineersComponent,
    HrProfileComponent,
    CvEngineersComponent
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
    MatButtonModule,
    ReactiveFormsModule,
  ]
})
export class PagesModule { }
