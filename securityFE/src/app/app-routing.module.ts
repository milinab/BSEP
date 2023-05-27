import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { HomeComponent } from "./modules/pages/home/home.component";
import {RegistrationComponent} from "./modules/pages/registration/registration.component";
import { AdminProfileComponent } from "./modules/pages/admin-profile/admin-profile.component";
import { UsersComponent } from "./modules/pages/users/users.component";
import { ProjectsComponent } from "./modules/pages/projects/projects.component";
import {ValidateRegistrationComponent} from "./modules/pages/validate-registration/validate-registration.component";
import { LoginComponent } from "./modules/pages/login/login.component";
import {EngineerProfileComponent} from "./modules/pages/engineer-profile/engineer-profile.component";
import {AuthGuard} from "./modules/guard/authGuard";

const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'registration', component: RegistrationComponent },
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
