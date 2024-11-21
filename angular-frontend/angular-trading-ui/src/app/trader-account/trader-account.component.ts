import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { InputFormComponent } from '../input-form/input-form.component';
import { Trader } from '../trader';
import { TraderListService } from '../trader-list.service';

@Component({
  selector: 'app-trader-account',
  templateUrl: './trader-account.component.html',
  styleUrls: ['./trader-account.component.css']
})
export class TraderAccountComponent implements OnInit {
  readonly dialog;
  trader: Trader | undefined = undefined;
  traderId: number = -1;

  constructor(private activatedRoute: ActivatedRoute, private traderListService: TraderListService, private matDialog: MatDialog) { 
    this.dialog = matDialog
  }

  ngOnInit(): void {
    this.traderId = Number(this.activatedRoute.snapshot.paramMap.get("id"))
    this.trader = this.traderListService.getTrader(this.traderId)
    
  }

  deposit() {
    console.log("deposit")
    let dialogRef = this.dialog.open(InputFormComponent, {
      height: '400px',
      width: '600px',
      data: {action: 'despoit'}
    });
    dialogRef.afterClosed().subscribe(deposit => {
      if(this.trader && deposit) this.trader.amount = this.trader.amount + deposit
    })
  }

  withdraw() {
    console.log("withdraw")
    let dialogRef = this.dialog.open(InputFormComponent, {
      height: '400px',
      width: '600px',
      data: {action: 'withdraw'}
    });
    dialogRef.afterClosed().subscribe(withdraw => {
      if(this.trader && withdraw) this.trader.amount = this.trader.amount - withdraw
    })
  }
}
