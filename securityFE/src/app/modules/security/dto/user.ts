export class UserDto{
    id: number = 0;
    email: string = '';
    password: string = '';
    firstName: string = '';
    lastName: string = '';

 public constructor(obj?: any) {
    if (obj) {
      this.id = obj.id;
      this.email = obj.username;
      this.password = obj.password;
      this.firstName = obj.firstName;
      this.lastName = obj.lastName;
    }
  }
}