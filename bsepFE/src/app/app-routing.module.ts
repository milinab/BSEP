import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { HomeComponent } from "./modules/pages/home/home.component";
import {AllCertificatesComponent} from "./modules/pages/all-certificates/all-certificates.component";

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'all-certificates', component: AllCertificatesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
