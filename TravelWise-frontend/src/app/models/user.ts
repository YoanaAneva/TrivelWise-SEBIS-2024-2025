export class User {
  id?: number;
  email: string;
  password: string;
  firstName: string;
  surname: string;
  birthDate: string | null;
  phoneNumber: string | null
  profilePictureUrl?: string;
  cartId?: number;

  constructor(email: string, password: string, firstName: string, surname: string, birthDate: string | null, phoneNumber: string | null) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.surname = surname;
    this.birthDate = birthDate;
    this.phoneNumber = phoneNumber;
  }
}
