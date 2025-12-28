export class OfferFilters {
  categoryId: number;
  minPrice?: number;
  maxPrice?: number;
  startsAfter?: string;
  endsBefore?: string;
  minAvailableSpots?: number

  constructor(categoryId: number, minPrice: number | undefined, maxPrice: number | undefined,
              startsAfter: string | undefined, endsBefore: string | undefined,
              minAvailableSpots: number | undefined) {
    this.categoryId = categoryId;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.startsAfter = startsAfter;
    this.endsBefore = endsBefore;
    this.minAvailableSpots = minAvailableSpots;
  }
}
