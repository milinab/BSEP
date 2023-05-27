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

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'admin-profile', component: AdminProfileComponent},
  { path: 'users', component: UsersComponent },
  { path: 'projects', component: ProjectsComponent },
  { path: 'validate-registration', component: ValidateRegistrationComponent },
  { path: 'manager-profile', component: ManagerProfileComponent},
  { path: 'manager-profile/update', component: ManagerProfileUpdateComponent},
  { path: 'change-password', component: ChangePasswordComponent},
  { path: 'manager-past-projects', component: ManagerPastProjectsComponent},
  { path: 'manager-current-projects', component: ManagerCurrentProjectsComponent},
  { path: 'project-employees', component: ProjectEmployeesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
