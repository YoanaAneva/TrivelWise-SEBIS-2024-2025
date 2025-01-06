export class Offer {
  id?: number;
  title: string;
  description: string;
  location: string;
  price: number;
  startDate: string;
  endDate: string;
  availableSpots: number;
  categoryId: number;
  imageUrls?: string[];

  constructor(title: string, description: string, location: string, price: number, startDate: string, endDate: string, availableSpots: number, categoryId: number) {
    this.title = title;
    this.description = description;
    this.location = location;
    this.price = price;
    this.startDate = startDate;
    this.endDate = endDate;
    this.availableSpots = availableSpots;
    this.categoryId = categoryId;
  }
}
