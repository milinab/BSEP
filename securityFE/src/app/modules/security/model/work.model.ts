import { Project } from "./project.model";

export class Work{
    id: number = 0;
    worker: Work = new Work();
    project: Project = new Project();
  
  
    public constructor(obj?: any) {
      if (obj) {
        this.id = obj.id;
        this.worker = obj.worker;
        this.project = obj.project;
      }
    }
  }
  