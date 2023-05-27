import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-all-workers-by-project',
  templateUrl: './all-workers-by-project.component.html',
  styleUrls: ['./all-workers-by-project.component.css']
})
export class AllWorkersByProjectComponent implements OnInit {

  constructor(private http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

}
