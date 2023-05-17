import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { HomeComponent } from "./modules/pages/home/home.component";
import {RegistrationComponent} from "./modules/pages/registration/registration.component";
import {ValidateRegistrationComponent} from "./modules/pages/validate-registration/validate-registration.component";

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'validate-registration', component: ValidateRegistrationComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
