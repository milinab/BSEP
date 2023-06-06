import {AppUser} from "./appUser.model";

export class Attachment{
  id: number = 0;
  fileName: string = '';
  fileType: string = '';
  data: any;
  appUser: AppUser = new AppUser();

  public constructor(obj?: any) {
    if(obj){
      this.id = obj.id;
      this.fileName = obj.fileName;
      this.fileType = obj.fileType;
      this.data = obj.data;
      this.appUser = obj.appUser;
    }
  }

}
