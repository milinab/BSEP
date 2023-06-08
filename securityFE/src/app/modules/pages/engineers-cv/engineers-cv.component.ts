import { Component, OnInit } from '@angular/core';
import { AppUser } from '../../security/model/appUser.model';
import { CvService } from "../../security/service/cv.service";

@Component({
  selector: 'app-engineers-cv',
  templateUrl: './engineers-cv.component.html',
  styleUrls: ['./engineers-cv.component.css']
})
export class EngineersCvComponent implements OnInit {
  engineerUsers: AppUser[] | undefined;

  constructor(private cvService: CvService) { }

  ngOnInit(): void {
    this.getEngineerUsers();
  }

  getEngineerUsers(): void {
    this.cvService.getAllCvs().subscribe(users => {
      this.engineerUsers = users.map(cv => cv.appUser);
    });
  }

  showCv(appUserId: number): void {
    this.cvService.downloadCv(appUserId).subscribe(data => {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const cvContent = e.target.result;
        window.open(cvContent, '_blank');
      };
      reader.readAsDataURL(data);
    });
  }
}
