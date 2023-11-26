export class CertificateIssuerDTOModel {
  organization: string = '';
  commonName: string = '';
  type: boolean | undefined;
  alias: string = '';

  public constructor(obj?: any) {
    if (obj) {
      this.commonName = obj.commonName;
      this.organization = obj.organization;
      this.type = obj.type;
      this.alias = obj.alias;
    }
  }
}
