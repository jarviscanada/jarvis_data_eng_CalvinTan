import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Trader } from '../trader';

@Component({
  selector: 'app-trader-edit-dialog',
  templateUrl: './trader-edit-dialog.component.html',
  styleUrls: ['./trader-edit-dialog.component.css']
})
export class TraderEditDialogComponent implements OnInit {
  trader: Trader
  dialogRef: MatDialogRef<TraderEditDialogComponent>

  constructor(@Inject(MAT_DIALOG_DATA) data: Trader, dialogRef: MatDialogRef<TraderEditDialogComponent>) {
    this.trader = {...data}
    this.dialogRef = dialogRef
   }

  ngOnInit(): void {
  }

  submit(): void{
    this.dialogRef.close(this.trader) 
  }

  close(): void {
    this.dialogRef.close()
  }
}
