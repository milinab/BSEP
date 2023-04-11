import { Component, OnInit } from '@angular/core';
import {RoomService} from "../../hospital/services/room.service";
import {CertificateModel} from "../../hospital/model/certificate.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public certificate: CertificateModel = new CertificateModel();

  constructor(private roomService: RoomService) { }

  ngOnInit(): void {
  }

  createCertificate(): void {
    try {
      this.roomService.createCertificate(this.certificate).subscribe(res => {
        alert("Certificate created.")
      })
    } catch (error) {
      alert(error)
    }
  }

}
