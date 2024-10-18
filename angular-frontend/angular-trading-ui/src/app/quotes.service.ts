import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Quote } from './quote';

@Injectable({
  providedIn: 'root'
})
export class QuotesService {
  httpClient: HttpClient
  quotesList: Quote[] = []

  constructor( httpClient: HttpClient) { 
    this.httpClient = httpClient
  }

  getColumns(): string[] {
    return ['ticker', 'lastPrice', 'bidPrice', 'bidSize', 'askPrice', 'askSize']
  }

  async getDataSource(): Promise<Observable<Quote[]>> {
    await this.loadQuotes().then(quotes => {
      this.quotesList = quotes
      console.log(this.quotesList)
    })
    console.log(this.quotesList)
    return of(this.quotesList)  
  }

  async loadQuotes(): Promise<Quote[]> {
    let temp = await this.httpClient.get('http://localhost:3100/api/quotes/', {responseType: 'text'}).toPromise()
    return <Quote[]> JSON.parse(temp)
  }
}
