import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {CertificateService} from "../../hospital/services/certificate.service";
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-all-certificates',
  templateUrl: './all-certificates.component.html',
  styleUrls: ['./all-certificates.component.css']
})
export class AllCertificatesComponent implements OnInit {

  rootCertificates: String | undefined;
  intermediaryCertificates: String | undefined;
  endEntityCertificates: String | undefined;

  constructor(private certificateService: CertificateService) { }

  ngOnInit() {
    this.getRootCertificates();
    this.getIntermediaryCertificates();
    this.getEndEntityCertificates();
  }

  getRootCertificates() {
    this.certificateService.getRootCerts().subscribe(
      certificates => {
        this.rootCertificates = certificates;
      },
      error => {
        console.log('Error fetching root certificates', error);
      }
    );
  }

  getIntermediaryCertificates() {
    this.certificateService.getIntermediaryCerts().subscribe(
      certificates => {
        this.intermediaryCertificates = certificates;
      },
      error => {
        console.log('Error fetching intermediary certificates', error);
      }
    );
  }

  getEndEntityCertificates() {
    this.certificateService.getEndEntityCerts().subscribe(
      certificates => {
        this.endEntityCertificates = certificates;
      },
      error => {
        console.log('Error fetching end-entity certificates', error);
      }
    );
  }
  downloadCertificate(certificateId: string) {
    this.certificateService.downloadCertificate(certificateId).subscribe((data: Blob) => {
      saveAs(data, 'certificate.txt');
    });
  }

}
