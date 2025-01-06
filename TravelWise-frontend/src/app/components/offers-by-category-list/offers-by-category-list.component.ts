import {Component, Input} from '@angular/core';
import {NavBarComponent} from '../nav-bar/nav-bar.component';
import {Category} from '../../models/category';
import {Offer} from '../../models/offer';
import {ActivatedRoute, Router} from '@angular/router';
import {OfferService} from '../../services/offer.service';
import {MatGridList, MatGridTile} from '@angular/material/grid-list';
import {CategoryService} from '../../services/category.service';
import {NgForOf} from '@angular/common';
import {OfferCardComponent} from '../offer-card/offer-card.component';
import {MatPaginator, PageEvent} from '@angular/material/paginator';

@Component({
  selector: 'app-offers-by-category-list',
  imports: [NavBarComponent, MatGridList, MatGridTile, NgForOf, OfferCardComponent, MatPaginator],
  templateUrl: './offers-by-category-list.component.html',
  standalone: true,
  styleUrl: './offers-by-category-list.component.css'
})
export class OffersByCategoryListComponent {
  category: Category = {} as Category;
  pageSize: number = 6;
  currentPage: number = 0;
  numOfItems: number = 10;
  offers: Offer[] = [];

  constructor(private offerService: OfferService,private categoryService: CategoryService,
              private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      const categoryId = Number(params['categoryId']);
      this.categoryService.getById(categoryId).subscribe(data => {
        this.category = data;
      });
      this.getOffersByCategory(this.currentPage, this.pageSize);
    });
  }

  getOffersByCategory(currentPage: number, pageSize: number) {
    let categoryId: number = Number(this.route.snapshot.paramMap.get('categoryId'));
    this.offerService.getOffersByCategory(categoryId, currentPage, pageSize).subscribe((data) => {
        this.offers = data;
        if(this.offers.length < 6) {
          this.numOfItems = this.offers.length;
        } else {
          this.numOfItems = 7;
        }
      });
  }

  pageChanged(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.getOffersByCategory(this.currentPage, this.pageSize);
  }
}
