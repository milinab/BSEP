export class CertificateStatusDTOModel{
  alias: string = '';
  status: boolean = true;

  public constructor(obj?: any) {
    this.alias = obj.alias;
    this.status = obj.status;
  }
}
