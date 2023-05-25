export class UserToken {
    sub: string = '';
    id: string = '';
    role: number = 0;
  
    constructor(sub: string, id: string, role: number ) {
      this.sub = sub;
      this.id = id;
      this.role = role;
    }
  }
  