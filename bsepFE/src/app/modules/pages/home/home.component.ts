import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {RoomService} from "../../hospital/services/room.service";
import {CertificateModel} from "../../hospital/model/certificate.model";
import {CertificateService} from "../../hospital/services/certificate.service";
import {FormControl, Validators} from "@angular/forms";
import {CertificateIssuerDTOModel} from "../../hospital/model/certificateIssuerDTO.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  dateFormControl = new FormControl('', [Validators.required, Validators.required]);
  certificate: CertificateModel[] = [];
  public issuerList: CertificateIssuerDTOModel[] = [];
  public selectedItem: string='';
  public alias: string | undefined;
  public nesto: boolean | undefined;
  @Output() onSelectedIssuer: EventEmitter<string> = new EventEmitter()

  constructor(private certificateService: CertificateService) { }

  ngOnInit(): void {
    this.certificateService.getIssues().subscribe(data => {
      this.issuerList = data;
    });
    this.certificate.push(new CertificateModel());
    this.certificate.push(new CertificateModel());
  }
  onSelect(issue: any) {
    this.selectedIssuer = issue;
    // do something with the selected issue
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
  selectedIssuer(issuer: any) {
    let alias = this.selectedItem;
    //console.log(this.selectedItem);
    //console.log(this.selectedItem);
    this.certificateService.getIssueByAlias(alias).subscribe((issuer: any) => {
      console.log(issuer.alias);
      this.nesto = issuer.type;
      this.certificate[1].organization = issuer.organization;
      this.certificate[1].commonName = issuer.commonName;
      this.certificate[1].location.country = "aaa";
      this.certificate[1].location.city = "bbbb";
      this.certificate[1].location.state = "aaaa";
    });
  }


}
