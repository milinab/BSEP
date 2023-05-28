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
import {ManagerGuard} from "./modules/guard/managerGuard";
import {AdminGuard} from "./modules/guard/adminGuard";
import {A} from "@angular/cdk/keycodes";
import {EngineerGuard} from "./modules/guard/engineerGuard";
import {ForbiddenPageComponent} from "./modules/pages/forbidden-page/forbidden-page.component";

const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'registration', component: RegistrationComponent },
  { path: 'admin-profile', component: AdminProfileComponent, canActivate: [AdminGuard, AuthGuard]},
  { path: 'users', component: UsersComponent },
  { path: 'all-workers-by-project', component: AllWorkersByProjectComponent},
  { path: 'manager-profile', component: ManagerProfileComponent, canActivate: [ManagerGuard, AuthGuard]},
  { path: 'manager-profile/update', component: ManagerProfileUpdateComponent, canActivate: [ManagerGuard, AuthGuard]},
  { path: 'change-password', component: ChangePasswordComponent, canActivate: [AuthGuard, ManagerGuard]},
  { path: 'manager-past-projects', component: ManagerPastProjectsComponent, canActivate: [ManagerGuard, AuthGuard]},
  { path: 'manager-current-projects', component: ManagerCurrentProjectsComponent, canActivate: [ManagerGuard, AuthGuard]},
  { path: 'project-employees', component: ProjectEmployeesComponent, canActivate: [AuthGuard, ManagerGuard]},
  { path: 'admin-profile', component: AdminProfileComponent, canActivate: [AuthGuard, AdminGuard]},
  { path: 'users', component: UsersComponent, canActivate: [AuthGuard] },
  { path: 'projects', component: ProjectsComponent, canActivate: [AuthGuard] },
  { path: 'validate-registration', component: ValidateRegistrationComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent},
  { path: 'forbidden-page', component: ForbiddenPageComponent},
  { path: 'engineer-profile', component: EngineerProfileComponent, canActivate: [AuthGuard, EngineerGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
