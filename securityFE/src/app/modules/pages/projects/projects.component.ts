import { Component, OnInit } from '@angular/core';
import { ProjectService } from '../../security/service/project.service';
import { Router } from '@angular/router';
import { Project } from '../../security/model/project.model';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {

  public projects: Project[] = [];
  public project: Project = new Project();

  constructor(private projectService : ProjectService, private router : Router) { }

  ngOnInit(): void {
    this.projectService.getProjects().subscribe(res =>{
      this.projects = res;
      console.log(res)
    })
  }

  // onProjectClick(project: any): void {
  //   console.log('Project ID:', project.id);
  //   this.router.navigate(['/all-workers-by-project', project.id]);
  // }
  
  onProjectClick(project: Project): void {
    this.router.navigate(['/all-workers-by-project'], { queryParams: { project: JSON.stringify(project.id) } });
    console.log("ID PROJ "+ project.id);
  }

  createProject(): void {
    try {
        this.projectService.createProject(this.project).subscribe(res => {
        alert("Project created.")
        })
        window.location.reload();
    } catch (error) {
      alert(error)
    }
  }

}
