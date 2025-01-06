export class TravelerDetails {
  id?: null;
  travelerFirstName: string;
  travelerSurname: string;
  travelerEmail: string;
  travelerPhoneNumber: string;
  reservationId?: number;

  constructor(travelerFirstName: string, travelerSurname: string, travelerEmail: string, travelerPhoneNumber: string) {
    this.travelerFirstName = travelerFirstName;
    this.travelerSurname = travelerSurname;
    this.travelerEmail = travelerEmail;
    this.travelerPhoneNumber = travelerPhoneNumber;
  }
}
