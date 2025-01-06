import { Component } from '@angular/core';
import {NavBarComponent} from '../nav-bar/nav-bar.component';
import {MatButtonModule} from '@angular/material/button';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';
import {MatIcon} from '@angular/material/icon';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-log-in',
  imports: [NavBarComponent, MatButtonModule, FormsModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatIcon, NgIf],
  templateUrl: './log-in.component.html',
  standalone: true,
  styleUrl: './log-in.component.css'
})
export class LogInComponent {
  logInForm!: FormGroup;
  hidePassword: boolean = true;
  userExists: boolean = true;

  constructor(private userService: UserService, private formBuilder: FormBuilder, private router: Router) {
    this.logInForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    })
  }
  logIn() {
    const email = this.logInForm.value.email;
    const password = this.logInForm.value.password;
    this.userService.login(email, password).subscribe({
      next: (user) => {
        sessionStorage.setItem('isLoggedIn', 'true');
        sessionStorage.setItem('userId', String(user.id));
        sessionStorage.setItem('cartId', String(user.cartId));
        if (user.profilePictureUrl !== undefined){
          sessionStorage.setItem('profilePicUrl', user.profilePictureUrl);
        }
        this.router.navigate(['']);
      },
      error: (err) => {
        if (err.status === 404) {
          this.userExists = false;
          this.logInForm.get('email')?.setErrors({noEmail: true});
          this.logInForm.get('password')?.setErrors({noPassword: true});
        }
      }
    })
  }

}
