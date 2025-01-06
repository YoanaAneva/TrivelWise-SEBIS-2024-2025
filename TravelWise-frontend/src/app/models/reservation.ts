import {TravelerDetails} from './travelerDetails';
import {from} from 'rxjs';

export class Reservation{
   id?: number;
   reservationNum: string;
   totalPrice: number;
   reservationDate: string;
   paid: boolean;
   offerId: number;
   cartId: number;
   travelers: TravelerDetails[];

   offerTitle?: string;
   offerImageUrl?: string;

   constructor(reservationNum: string, totalPrice: number, reservationDate: string, offerId: number, cartId: number, travelers: TravelerDetails[]) {
     this.reservationNum = reservationNum;
     this.totalPrice = totalPrice;
     this.reservationDate = reservationDate;
     this.paid = false;
     this.offerId = offerId;
     this.cartId = cartId;
     this.travelers = travelers;
   }
}
