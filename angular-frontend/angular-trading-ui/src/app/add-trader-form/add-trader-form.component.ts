import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Trader } from '../trader';
import { TraderListService } from '../trader-list.service';

@Component({
  selector: 'app-add-trader-form',
  templateUrl: './add-trader-form.component.html',
  styleUrls: ['./add-trader-form.component.css']
})
export class AddTraderFormComponent implements OnInit {
  firstName: string = ''
  lastName: string = ''
  dateOfBirth: string = ''
  country: string = ''
  email: string = ''
  trader: Trader | undefined 
  dialogRef: MatDialogRef<AddTraderFormComponent>
  traderListService: TraderListService

  constructor(dialogRef: MatDialogRef<AddTraderFormComponent>, traderListService: TraderListService) {
    this.trader = undefined
    this.dialogRef = dialogRef
    this.traderListService = traderListService
  }
    
  ngOnInit(): void {
  }

  submit(): void{
    const newId = this.traderListService.genNextId()
    this.trader = {
      key: newId.toString(),
      id: newId,
      firstName: this.firstName,
      lastName: this.lastName,
      dob: this.dateOfBirth,
      country: this.country,
      email: this.email,
      amount: 0
    }
    console.log(this.trader)
    this.dialogRef.close(this.trader)
  }

  close(): void {
    this.dialogRef.close()
  }
}
