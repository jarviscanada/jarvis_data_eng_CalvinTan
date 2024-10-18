import { Component, OnInit } from '@angular/core';
import { TraderListService } from '../trader-list.service';
import { Trader } from '../trader';
import { Observable } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { MatDialog } from '@angular/material/dialog';
import { AddTraderFormComponent } from '../add-trader-form/add-trader-form.component';
import { TraderEditDialogComponent } from '../trader-edit-dialog/trader-edit-dialog.component';

@Component({
  selector: 'app-trader-list',
  templateUrl: './trader-list.component.html',
  styleUrls: ['./trader-list.component.css']
})
export class TraderListComponent implements OnInit {
  readonly dialog: MatDialog
  dataSource: any = null
  columnsToDisplay: string[]
  traderListService: TraderListService

  constructor(service: TraderListService, matDialog: MatDialog) {
    this.dialog = matDialog
    this.traderListService = service
    this.columnsToDisplay = this.traderListService.getColumns()
    this.traderListService.getDataSource().subscribe(val => {
      this.dataSource = new MatTableDataSource(val)
    })
  }

  ngOnInit(): void {
  }

  addTrader(): void {
    let dialogRef = this.dialog.open(AddTraderFormComponent, {
      height: '400px',
      width: '600px'
    })
    dialogRef.afterClosed().subscribe(trader => {
      this.traderListService.addTrader(trader)
      this.traderListService.getDataSource().subscribe(val => {
        this.dataSource = new MatTableDataSource(val)
      })
    })
  }

  deleteTrader(event: Event, id: number): void {
    try {
      this.traderListService.deleteTrader(id)
      this.traderListService.getDataSource().subscribe(val => {
        this.dataSource = new MatTableDataSource(val)
      })
    } catch(err) {
      console.log(err)
    }
  }

  editTrader(trader: Trader): void {
    let dialogRef = this.dialog.open(TraderEditDialogComponent, {
      height: '400px',
      width: '600px',
      data: trader})
    dialogRef.afterClosed().subscribe(trader => {
      if (trader) this.traderListService.updateTrader(trader)
      this.traderListService.getDataSource().subscribe(val => {
        this.dataSource = new MatTableDataSource(val)
      })
    })
  }
}
