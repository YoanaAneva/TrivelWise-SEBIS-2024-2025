import { Component } from '@angular/core';
import { MatToolbar } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import {Department} from '../../models/department';
import {DepartmentService} from '../../services/department.service';
import {NgForOf, NgIf} from '@angular/common';
import {Category} from '../../models/category';
import {CategoryService} from '../../services/category.service';
import {Router, RouterLink} from '@angular/router';
import {CartContentComponent} from '../cart-content/cart-content.component';

@Component({
  selector: 'app-nav-bar',
  imports: [MatToolbar, MatButtonModule, MatMenuModule, NgForOf, NgIf, RouterLink, CartContentComponent],
  templateUrl: './nav-bar.component.html',
  standalone: true,
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent {
  departments: Department[] = [];
  categories: Category[] = [];
  isUserLoggedIn: boolean;
  userProfilePic: string | null;
  isCartOpen: boolean = false;

  constructor(private departmentService: DepartmentService, private categoryService: CategoryService, private router: Router) {
    this.isUserLoggedIn = Boolean(sessionStorage.getItem('isLoggedIn'));
    this.userProfilePic = sessionStorage.getItem('profilePicUrl');
  }

  ngOnInit() {
    this.getDepartments();
    this.getCategories()
  }

  getDepartments() {
    this.departmentService.getAll().subscribe((departments) => {
      this.departments = departments;
    })
  }

  getCategories() {
    this.categoryService.getAll().subscribe((categories) => {
      this.categories = categories;
    })
  }

  getCategoriesForDepartment(departmentId: number): Category[]{
    return this.categories.filter(category => category.departmentId == departmentId);
  }

  toggleCart() {
    this.isCartOpen = !this.isCartOpen;
  }

  navigateToHomePage(){
    this.router.navigate(['']);
  }

  navigateToRegistration(){
    this.router.navigate(['registration']);
  }

  navigateToLogIn() {
    this.router.navigate(['log-in']);
  }

  navigateToCategoryList(categoryId: number) {
    this.router.navigate(['offers-by-category-list', categoryId]);
  }

  protected readonly sessionStorage = sessionStorage;
}
