import { Component } from '@angular/core';
import {NavBarComponent} from '../nav-bar/nav-bar.component';
import {MatButtonModule} from '@angular/material/button';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import {AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule,
        ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {UserService} from '../../services/user.service';
import {MatIcon} from '@angular/material/icon';
import {User} from '../../models/user';
import {NgIf} from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-registration',
  imports: [NavBarComponent, MatButtonModule, FormsModule, ReactiveFormsModule, MatDatepickerModule,
    MatNativeDateModule, MatFormFieldModule, MatInputModule, MatIcon, NgIf],
  templateUrl: './registration.component.html',
  standalone: true,
  styleUrl: './registration.component.css'
})
export class RegistrationComponent {
  registrationForm!: FormGroup
  profilePic: File | null = null;
  isSubmitPressed: boolean = false;
  hidePassword = true;
  hideRepPassword = true;


  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router) {
    this.registrationForm = this.formBuilder.group({
        email: ['', [Validators.required, Validators.email]],
        password: ['', Validators.required],
        repPassword: ['', Validators.required],
        firstName: ['', Validators.required],
        surname: ['', Validators.required],
        birthdate: [null],
        phoneNumber: ['']
    }, {
      validators: this.passwordsMatchValidator() }
    );
  }

  handleFormSubmission() {
    this.isSubmitPressed = true;
    if(!this.registrationForm.valid) {
      return;
    }

    let birthdate: string | null = null;
    if(this.registrationForm.value.birthdate !== null) {
      birthdate = this.formatDateToString(this.registrationForm.get('birthdate')?.value);
    }
    let user: User = new User(this.registrationForm.value.email,
                          this.registrationForm.value.password,
                          this.registrationForm.value.firstName,
                          this.registrationForm.value.surname, birthdate,
                          this.registrationForm.value.phoneNumber);

    this.userService.createUser(user, this.profilePic).subscribe({
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
        if (err.status === 409) {
          this.registrationForm.get('email')?.setErrors({emailExists: true});
        }
      }
    });
  }

  passwordsMatchValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const password = control.get('password')?.value;
      const repPassword = control.get('repPassword')?.value;
      if (repPassword && password !== repPassword) {
        control.get('repPassword')?.setErrors({ passwordsMismatch: true });
        return { passwordsMismatch: true };
      }
      return null;
    };
  }

  onFileChange(event: any) {
    this.profilePic = event.target.files[0];
  }

  formatDateToString(date: Date) {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
}
