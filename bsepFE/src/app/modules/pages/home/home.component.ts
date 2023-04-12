import { Component, OnInit } from '@angular/core';
import {RoomService} from "../../hospital/services/room.service";
import {CertificateModel} from "../../hospital/model/certificate.model";
import {CertificateService} from "../../hospital/services/certificate.service";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  dateFormControl = new FormControl('', [Validators.required, Validators.required]);
  public certificate: CertificateModel = new CertificateModel();

  constructor(private certificateService: CertificateService) { }

  ngOnInit(): void {
  }

  createCertificate(): void {
    try {
      this.certificateService.createCertificate(this.certificate).subscribe(res => {
        alert("Certificate created.")
      })
    } catch (error) {
      alert(error)
    }
  }



}
