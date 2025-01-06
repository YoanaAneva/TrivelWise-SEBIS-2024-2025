export class ChargeRequest {
  amount: number;
  name: string;
  currency: string;

  constructor(amount: number, name: string, currency: string) {
    this.amount = amount;
    this.name = name;
    this.currency = currency;
  }
}
