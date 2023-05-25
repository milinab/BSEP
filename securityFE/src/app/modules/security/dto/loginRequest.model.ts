export class LoginRequest {
    email: string = '';
    password: string = '';
  
    public constructor(obj?: any) {
      if (obj) {
        this.email = obj.email;
        this.password = obj.password;
      }
    }
  }
  