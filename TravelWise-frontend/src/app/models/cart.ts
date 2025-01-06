export class Cart {
  id?: number;
  totalPrice: number;
  numberOfItems: number;
  userId: number;

  constructor(totalPrice: number, numberOfItems: number, userId: number) {
    this.totalPrice = totalPrice;
    this.numberOfItems = numberOfItems;
    this.userId = userId;
  }
}
