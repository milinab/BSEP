import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }
  public profile(): void {
    this.router.navigate(['/manager-profile'])
  }

  public signOut(): void {
    this.router.navigate(['/login'])
  }
}
