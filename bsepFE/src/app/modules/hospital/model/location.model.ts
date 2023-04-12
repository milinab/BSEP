export class Location {
  postalCode: number = 0;
  city: string = '';
  state: string = '';
  country: string = '';
  streetName: string = '';
  streetNumber: string = '';


  public constructor(obj?: any) {
    if (obj) {
      this.postalCode = obj.postalCode;
      this.city = obj.city;
      this.state = obj.state;
      this.country = obj.country;
      this.streetName = obj.streetName;
      this.streetNumber = obj.streetNumber;
    }
  }

}


