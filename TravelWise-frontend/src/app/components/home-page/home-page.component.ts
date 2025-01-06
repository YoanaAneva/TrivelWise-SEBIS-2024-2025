import { Component } from '@angular/core';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import {MatButton} from '@angular/material/button';
import {NgIf} from '@angular/common';
import {RecommendationsListComponent} from '../recommendations-list/recommendations-list.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home-page',
  imports: [NavBarComponent, MatButton, NgIf, RecommendationsListComponent],
  templateUrl: './home-page.component.html',
  standalone: true,
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {
  isUserLoggedIn: boolean;

  constructor(private router: Router) {
    this.isUserLoggedIn = Boolean(sessionStorage.getItem('isLoggedIn'));
  }

  navigateToRegistration() {
    this.router.navigate(['registration'])
  }

  navigateToLogIn() {
    this.router.navigate(['log-in'])
  }
}
