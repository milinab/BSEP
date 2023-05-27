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
import { LoginComponent } from './login/login.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import { MatInputModule } from '@angular/material/input';
import { AllWorkersByProjectComponent } from './all-workers-by-project/all-workers-by-project.component';
import { EngineerProfileComponent } from './engineer-profile/engineer-profile.component';

@NgModule({
  declarations: [
    HomeComponent,
    RegistrationComponent,
    AdminProfileComponent,
    ProjectsComponent,
    UsersComponent,
    ValidateRegistrationComponent,
    LoginComponent,
    AllWorkersByProjectComponent,
    EngineerProfileComponent
  ],
    imports: [
        CommonModule,
        AppRoutingModule,
        FormsModule,
        MatTableModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule
    ]
})
export class PagesModule { }
