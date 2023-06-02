import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { WorkService } from '../../security/service/work.service';
import { AppUserService } from '../../security/service/appUser.service';
import { Project } from '../../security/model/project.model';
import { AppUser } from '../../security/model/appUser.model';

@Component({
  selector: 'app-all-workers-by-project',
  templateUrl: './all-workers-by-project.component.html',
  styleUrls: ['./all-workers-by-project.component.css']
})
export class AllWorkersByProjectComponent implements OnInit {

  public project: Project = new Project();
  public projectId: number = 0;
  public users: AppUser[] = [];
  public selectedWorker: number = 0;
  public workers: AppUser[] = [];

  constructor(private http: HttpClient, private route: ActivatedRoute, private workService: WorkService, private appUserService: AppUserService) { }

  ngOnInit(): void {



    this.projectId = Number(this.route.snapshot.queryParamMap.get('project'));
    console.log('Project ID:', this.projectId);
    //getProject by Id .. subscribe(res => {
    //this.project = res;
    //|]})
    if (this.project) {
      console.log("Ako proj postoji")
      this.showWorkers();

    }

  }

  showWorkers(): void {
    this.workService.getAllWorkersByProject(this.projectId).subscribe(users => {
      this.users = users;
      this.appUserService.getUsers().subscribe(res => {
        this.workers = res;
        this.filter();
      })
    });

  }

  filter(): void {
    const workerToProject: number[] = [];
    this.users.forEach(w => workerToProject.push(w.id));

    console.log("Workers to project: ", workerToProject)

    this.workers = this.workers.filter(w => {
      let exist = false;
      let isAdmin = false;
      let isHR = false;
      workerToProject.forEach(wop => {
        if (wop === w.id) {
          console.log("Postoji na projektu user, ", wop);
          exist = true;
        }
      })

      if (w.appUserRole === 'ADMIN') isAdmin = true;
      if (w.appUserRole === 'HR') isHR = true;

      return !exist && !isAdmin && !isHR;
    });
  }

  addWorkerToProject() {
    console.log("addWorker to Project with id: ", this.projectId);

    // Check if the selected worker already exists in the project\
    console.log("this.users ", this.users)

    const workerAlreadyExists = this.users.some(user => user.id === this.selectedWorker);

    if (workerAlreadyExists) {
      console.log("Worker already exists in the project.");
      return;
    }

    const workerId: number[] = [];
    workerId.push(this.selectedWorker);
    console.log("Id workera: ", workerId)
    const payload = {
      projectId: this.projectId,
      workersId: workerId
    };
    this.workService.addWorkersToProjects(payload).subscribe(data => console.log(data));
    //window.location.reload();
  }

}
