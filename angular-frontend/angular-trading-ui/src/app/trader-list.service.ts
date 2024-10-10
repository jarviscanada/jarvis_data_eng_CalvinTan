import { Injectable } from '@angular/core';
import { of, Observable } from 'rxjs';
import { Trader } from './trader';

@Injectable({
  providedIn: 'root'
})
export class TraderListService {
  traderList: Trader[] = [
    {
      key: '1', id: 1, firstName: 'Mike', lastName: 'Spencer', dob: new Date().toLocaleDateString(),
      country: 'Canada', email: 'mike@test.com', amount: 0
      // actions: `<button mat-button (click)="deleteTrader()">Delete Trader</button>`
    },
    {
      key: '2', id: 2, firstName: 'Sike', lastName: 'Mencer', dob: new Date().toLocaleDateString(),
      country: 'Canada', email: 'sike@test.com', amount: 0,
    },
    {
      key: '2', id: 2, firstName: 'Sike', lastName: 'Mencer', dob: new Date().toLocaleDateString(),
      country: 'Canada', email: 'sike@test.com', amount: 0,
    }
  ]
  constructor() {}
  getDataSource(): Observable<Trader[]> {
    return of(this.traderList);
  }
  getColumns(): string[] {
    return ['firstName', 'lastName', 'email', 'dateOfBirth', 'country', 'actions']
  }

  deleteTrader(id: number): void {
    this.traderList = this.traderList.filter(trader => trader.id != id)
  }
}
