import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffersByCategoryListComponent } from './offers-by-category-list.component';

describe('OffersByCategoryListComponent', () => {
  let component: OffersByCategoryListComponent;
  let fixture: ComponentFixture<OffersByCategoryListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OffersByCategoryListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OffersByCategoryListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
