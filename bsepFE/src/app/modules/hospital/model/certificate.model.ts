import {Location} from "./location.model";

export class CertificateModel {
  organization: string = '';
  commonName: string = '';
  location: Location = new Location();
  type: boolean | undefined;
  keyStorePassword: string = 'password';
  startDate: Date = new Date();
  endDate: Date = new Date();

  public constructor(obj?: any) {
    if (obj) {
      this.commonName = obj.commonName;
      this.organization = obj.organization;
      this.location = obj.location;
      this.type = obj.type;
      this.keyStorePassword = obj.keyStorePassword;
      this.startDate = obj.startDate;
      this.endDate = obj.endDate;
    }
  }
}
