export class Project{
    id: number = 0;
    name: string = "";
  
  
    public constructor(obj?: any) {
      if (obj) {
        this.id = obj.id;
        this.name = obj.name;
      }
    }
  }
  