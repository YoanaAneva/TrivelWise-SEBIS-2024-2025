import { Routes } from '@angular/router';
import {HomePageComponent} from './components/home-page/home-page.component';
import {RegistrationComponent} from './components/registration/registration.component';
import {OffersByCategoryListComponent} from './components/offers-by-category-list/offers-by-category-list.component';
import {OfferDetailsComponent} from './components/offer-details/offer-details.component';
import {LogInComponent} from './components/log-in/log-in.component';
import {UserDetailsComponent} from './components/user-details/user-details.component';
import {MakeReservationComponent} from './components/make-reservation/make-reservation.component';
import {SuccessfulPaymentComponent} from './components/successful-payment/successful-payment.component';
import {CancelPaymentComponent} from './components/cancel-payment/cancel-payment.component';

export const routes: Routes = [
  { path: '', component: HomePageComponent }, // Default route
  { path: 'registration', component: RegistrationComponent },
  { path: 'log-in', component: LogInComponent },
  { path: 'offers-by-category-list/:categoryId', component: OffersByCategoryListComponent },
  { path: 'offer-details/:offerId', component: OfferDetailsComponent},
  { path: 'user-details/:userId', component: UserDetailsComponent },
  { path: 'make-reservation/:offerId', component: MakeReservationComponent },
  { path: 'success', component: SuccessfulPaymentComponent },
  { path: 'cancel', component: CancelPaymentComponent }
];
