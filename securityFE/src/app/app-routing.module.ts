import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { HomeComponent } from "./modules/pages/home/home.component";
import {RegistrationComponent} from "./modules/pages/registration/registration.component";
import { AdminProfileComponent } from "./modules/pages/admin-profile/admin-profile.component";
import { UsersComponent } from "./modules/pages/users/users.component";
import { ProjectsComponent } from "./modules/pages/projects/projects.component";
import {ValidateRegistrationComponent} from "./modules/pages/validate-registration/validate-registration.component";
import {ManagerProfileComponent} from "./modules/pages/manager-profile/manager-profile.component";
import {ManagerProfileUpdateComponent} from "./modules/pages/manager-profile-update/manager-profile-update.component";
import {ChangePasswordComponent} from "./modules/pages/change-password/change-password.component";
import {ManagerPastProjectsComponent} from "./modules/pages/manager-past-projects/manager-past-projects.component";
import {
  ManagerCurrentProjectsComponent
} from "./modules/pages/manager-current-projects/manager-current-projects.component";
import {ProjectEmployeesComponent} from "./modules/pages/project-employees/project-employees.component";
import { LoginComponent } from "./modules/pages/login/login.component";
import { AllWorkersByProjectComponent } from "./modules/pages/all-workers-by-project/all-workers-by-project.component";
import {EngineerProfileComponent} from "./modules/pages/engineer-profile/engineer-profile.component";
import {AuthGuard} from "./modules/guard/authGuard";

const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'registration', component: RegistrationComponent },
  { path: 'admin-profile', component: AdminProfileComponent},
  { path: 'users', component: UsersComponent, canActivate: [AuthGuard] },
  { path: 'projects', component: ProjectsComponent, canActivate: [AuthGuard]},
  { path: 'validate-registration', component: ValidateRegistrationComponent },
  { path: 'login', component: LoginComponent},
  { path: 'all-workers-by-project', component: AllWorkersByProjectComponent, canActivate: [AuthGuard]},
  { path: 'manager-profile', component: ManagerProfileComponent},
  { path: 'manager-profile/update', component: ManagerProfileUpdateComponent},
  { path: 'change-password', component: ChangePasswordComponent},
  { path: 'manager-past-projects', component: ManagerPastProjectsComponent},
  { path: 'manager-current-projects', component: ManagerCurrentProjectsComponent},
  { path: 'project-employees', component: ProjectEmployeesComponent},
  { path: 'admin-profile', component: AdminProfileComponent, canActivate: [AuthGuard]},
  { path: 'users', component: UsersComponent, canActivate: [AuthGuard] },
  { path: 'projects', component: ProjectsComponent, canActivate: [AuthGuard] },
  { path: 'validate-registration', component: ValidateRegistrationComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent},
  { path: 'engineer-profile', component: EngineerProfileComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
