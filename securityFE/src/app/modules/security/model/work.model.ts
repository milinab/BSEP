import { Project } from "./project.model";

export class Work{
    id: number = 0;
    worker: Work = new Work();
    project: Project = new Project();
    description: string = '';
    duration: number = 0;
    startTime: Date = new Date;
    endTime: Date = new Date;

    public constructor(obj?: any) {
      if (obj) {
        this.id = obj.id;
        this.worker = obj.worker;
        this.project = obj.project;
        this.description = obj.description;
        this.duration = obj.duration;
        this.startTime = obj.startTime;
        this.endTime = obj.endTime;
      }
    }
  }
