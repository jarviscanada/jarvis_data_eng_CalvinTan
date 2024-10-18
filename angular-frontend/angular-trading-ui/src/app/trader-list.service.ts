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
    },
    {
      key: '2', id: 2, firstName: 'Sike', lastName: 'Mencer', dob: new Date().toLocaleDateString(),
      country: 'Canada', email: 'sike@test.com', amount: 0,
    },
    {
      key: '3', id: 3, firstName: 'Hike', lastName: 'Mencer', dob: new Date().toLocaleDateString(),
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

  getTrader(id: number): Trader | undefined {
    var result = this.traderList.find(trader => trader.id === id)
    if (result) {
      return result
    } else {
      console.log("trader: " + id + " not found")
    }
    return undefined
  }

  genNextId(): number {
    const traderWithMaxId = this.traderList.reduce(function(prev, current) {
      return (prev && prev.id > current.id) ? prev : current
    })
    return traderWithMaxId.id + 1;
  }

  updateAmount(id: number, newAmount: number) {
    const toUpdate = this.traderList.find(trader => trader.id === id)
    if (toUpdate) {
      toUpdate.amount = newAmount
    } else {
      console.log("id not found")
    }
  }

  addTrader(trader: Trader): void {
    this.traderList.push(trader)
  }

  deleteTrader(id: number): void {
    this.traderList = this.traderList.filter(trader => trader.id != id)
  }

  updateTrader(newTrader: Trader): void {
    let toUpdate = this.traderList.findIndex(trader => trader.id === newTrader.id)
    this.traderList[toUpdate] = {...newTrader}
  }
}
