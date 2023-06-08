import {AppUser} from "./appUser.model";

export class Cv{
  id: number = 0;
  path: string = '';
  appUser: AppUser = new AppUser();
  public constructor(obj?: any) {
    if(obj){
      this.id = obj.id;
      this.path = obj.path;
      this.appUser = obj.appUser;
    }
  }
}
