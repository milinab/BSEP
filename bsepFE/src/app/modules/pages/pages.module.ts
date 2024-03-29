import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { HomeComponent } from './home/home.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import {MaterialModule} from "../../material/material.module";
import { AllCertificatesComponent } from './all-certificates/all-certificates.component';
import {MatSelectModule} from "@angular/material/select";
import { CheckComponent } from './check/check.component';

@NgModule({
  declarations: [
    HomeComponent,
    AllCertificatesComponent,
    CheckComponent,
  ],
    imports: [
        CommonModule,
        AppRoutingModule,
        FormsModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MaterialModule,
        ReactiveFormsModule,
        MatSelectModule

    ]
})
export class PagesModule { }
