import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { QuotesService } from '../quotes.service';

@Component({
  selector: 'app-quotes-list',
  templateUrl: './quotes-list.component.html',
  styleUrls: ['./quotes-list.component.css']
})
export class QuotesListComponent implements OnInit {
  dataSource: any = null
  columnsToDisplay: string[]
  quoteService: QuotesService

  constructor(quotesService: QuotesService) {
    this.quoteService = quotesService
    this.columnsToDisplay = this.quoteService.getColumns()
    this.quoteService.getDataSource().then(res => res.subscribe(val => {
      this.dataSource = new MatTableDataSource(val)
    }))
   }

  ngOnInit(): void {
  }

}
