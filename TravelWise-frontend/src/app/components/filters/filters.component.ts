import {Component, EventEmitter, Output} from '@angular/core';
import {OfferFilters} from '../../models/offerFilters';
import {MatSlider, MatSliderRangeThumb, MatSliderThumb} from '@angular/material/slider';
import {MatLabel, MatSuffix} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import {MatButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-filters',
  imports: [
    MatSlider,
    MatSliderRangeThumb,
    MatLabel,
    MatInput,
    MatDatepickerInput,
    MatDatepickerToggle,
    MatDatepicker,
    MatSuffix,
    MatNativeDateModule,
    MatSliderThumb,
    MatButton,
    MatIcon,
    NgIf,
    FormsModule
  ],
  templateUrl: './filters.component.html',
  standalone: true,
  styleUrl: './filters.component.css'
})
export class FiltersComponent {
  @Output() filtersChanged = new EventEmitter<any>();

  minPrice?: number;
  maxPrice?: number;
  startsAfter?: Date;
  endsBefore?: Date;
  minSpotsAvailable?: number;
  areFiltersSelected: boolean = false;

  constructor() {
    this.minPrice = 0;
  }

  onSelectFilters() {
    this.areFiltersSelected = !this.areFiltersSelected;
  }

  resetFilters() {
    this.minPrice = undefined;
    this.maxPrice = undefined;
    this.startsAfter = undefined;
    this.endsBefore = undefined;
    this.minSpotsAvailable = undefined;
  }

  applyFilters() {
    if (this.minPrice === 0) {
      this.minPrice = undefined;
    }
    console.log("111111111111111")

    if (this.maxPrice === 0) {
      this.maxPrice = undefined;
    }
    console.log("2222222222222")
    let startsAfterStr: string | undefined;
    let endsBeforeStr: string | undefined;
    if (this.startsAfter) {
      startsAfterStr = this.formatDateToString(this.startsAfter)
    }
    console.log("333333")

    if (this.endsBefore) {
      endsBeforeStr = this.formatDateToString(this.endsBefore)
    }
    console.log("44444444444")

    const filters = new OfferFilters(0, this.minPrice,
                            this.maxPrice, startsAfterStr,
                            endsBeforeStr, this.minSpotsAvailable);

    console.log(filters);
    this.filtersChanged.emit(filters);
  }

  formatDateToString(date: Date) {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
}
