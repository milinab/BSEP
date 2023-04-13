import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { HomeComponent } from "./modules/pages/home/home.component";
import {AllCertificatesComponent} from "./modules/pages/all-certificates/all-certificates.component";
import {CheckComponent} from "./modules/pages/check/check.component";

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'all-certificates', component: AllCertificatesComponent},
  { path: 'check', component: CheckComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
