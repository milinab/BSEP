import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../../security/service/token-storage.service";
@Component({
  selector: 'app-forbidden-page',
  templateUrl: './forbidden-page.component.html',
  styleUrls: ['./forbidden-page.component.css']
})
export class ForbiddenPageComponent implements OnInit {
  @ViewChild('fileInput') fileInput: ElementRef | undefined;

  constructor(private router: Router, private route: ActivatedRoute, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {

  }

  public login(): void {
    this.router.navigate(['/login'])
  }

}
