import { Component } from '@angular/core';
import {NavBarComponent} from '../nav-bar/nav-bar.component';
import {User} from '../../models/user';
import {UserService} from '../../services/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Reservation} from '../../models/reservation';
import {ReservationService} from '../../services/reservation.service';
import {MatButton} from '@angular/material/button';
import {ReservationCardComponent} from '../reservation-card/reservation-card.component';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-user-details',
  imports: [NavBarComponent, MatButton, ReservationCardComponent, NgForOf],
  templateUrl: './user-details.component.html',
  standalone: true,
  styleUrl: './user-details.component.css'
})
export class UserDetailsComponent {
  user: User = {} as User;
  reservations: Reservation[] = [];

  constructor(private userService: UserService, private reservationService: ReservationService,
              private route: ActivatedRoute, private router: Router) {
    const userId = Number(this.route.snapshot.paramMap.get('userId'));
    userService.getById(userId).subscribe((data) => {
      this.user = data;
    });

    reservationService.getByUser(userId).subscribe((data) => {
      this.reservations = data;
      this.reservations = this.reservations.filter(reservation => reservation.paid);
    });
  }

  logOut() {
    sessionStorage.removeItem('isLoggedIn');
    sessionStorage.removeItem('userId');
    sessionStorage.removeItem('profilePicUrl');
    this.router.navigate(['']);
  }

  deleteAccount() {
    this.userService.deleteUser(this.user.id!).subscribe();
    sessionStorage.removeItem('userId');
    sessionStorage.removeItem('cartId');
    sessionStorage.removeItem('isLoggedIn');
    sessionStorage.removeItem('profilePicUrl');
    this.router.navigate(['']);
  }
}
