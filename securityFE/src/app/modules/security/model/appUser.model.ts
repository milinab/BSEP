import { getLocaleDateFormat } from "@angular/common";

export class AppUser{
  id: number = 0;
  email: string = '';
  password: string = '';
  firstName: string = '';
  lastName: string = '';
  appUserRole: string = '';
  startDate: string = '';
  endDate: string = '';
  blocked: boolean = false;


  public constructor(obj?: any) {
    if (obj) {
      this.id = obj.id;
      this.email = obj.username;
      this.password = obj.password;
      this.firstName = obj.firstName;
      this.lastName = obj.lastName;
      this.appUserRole = obj.appUserRole;
      this.startDate = obj.startDate;
      this.endDate = obj.endDate;
      this.blocked = obj.block;
    }
  }
}
