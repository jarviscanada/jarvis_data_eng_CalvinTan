import { Component, OnInit } from '@angular/core';
import { TraderListService } from '../trader-list.service';
import { Trader } from '../trader';
import { Observable } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-trader-list',
  templateUrl: './trader-list.component.html',
  styleUrls: ['./trader-list.component.css']
})
export class TraderListComponent implements OnInit {
  dataSource: any = null
  columnsToDisplay: string[]
  traderListService: TraderListService

  constructor(service: TraderListService, sanitizer: DomSanitizer) {
    this.traderListService = service
    this.traderListService.getDataSource().subscribe(val => {
      this.dataSource = new MatTableDataSource(val)
    })
    this.columnsToDisplay = this.traderListService.getColumns()
  }

  ngOnInit(): void {
  }

  deleteTrader(event: Event, id: number): void {
    console.log(id)
    try {
      this.traderListService.deleteTrader(id)
      this.traderListService.getDataSource().subscribe(val => {
        this.dataSource = new MatTableDataSource(val)
      })
    } catch(err) {
      console.log(err)
    }
  }
}
