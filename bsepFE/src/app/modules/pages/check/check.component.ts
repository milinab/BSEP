import { Component, OnInit } from '@angular/core';
import {CertificateStatusDTOModel} from "../../hospital/model/certificateStatusDTO.model";
import {CertificateService} from "../../hospital/services/certificate.service";

@Component({
  selector: 'app-check',
  templateUrl: './check.component.html',
  styleUrls: ['./check.component.css']
})
export class CheckComponent implements OnInit {

  alias: string | undefined ;
  certificateStatus: CertificateStatusDTOModel | undefined;

  constructor(private certificateService: CertificateService) {}

  getCertificateStatus() {
    this.certificateService.getCertificateStatusByAlias(this.alias)
      .subscribe((data: CertificateStatusDTOModel) => {
        this.certificateStatus = data;
        console.log(this.certificateStatus.status);
      }, (error) => {
        console.log(error);
      });
  }

  public revokeCertificate(alias: any) {
    this.certificateService.revokeCertificate(alias).subscribe( res =>
    {
      this.getCertificateStatus();
      alert("Success!")
    }, error => {
      alert("Can't revoke!")
    })
  }

  ngOnInit(): void {
  }

}
