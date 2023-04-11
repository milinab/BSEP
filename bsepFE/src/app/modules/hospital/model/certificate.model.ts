export class CertificateModel {
  commonName: string = '';
  organizationUnit: string = '';
  organizationName: string = '';
  localityName: string = '';
  stateName: string = '';
  country: string = '';


  public constructor(obj?: any) {
    if (obj) {
      this.commonName = obj.commonName;
      this.organizationUnit = obj.organizationUnit;
      this.organizationName = obj.organizationName;
      this.localityName = obj.localityName;
      this.stateName = obj.stateName;
      this.country = obj.country;
    }
  }
}
