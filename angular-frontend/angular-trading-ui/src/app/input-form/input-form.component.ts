import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-input-form',
  templateUrl: './input-form.component.html',
  styleUrls: ['./input-form.component.css']
})
export class InputFormComponent implements OnInit {
  action: string
  value: string = ''
  dialogRef: MatDialogRef<InputFormComponent>
  valueFormControl = new FormControl('', [Validators.required, Validators.pattern("^[0-9]*$")]);

  constructor(@Inject(MAT_DIALOG_DATA) data: {action: string}, dialogRef: MatDialogRef<InputFormComponent>) { 
    this.action = data.action
    this.dialogRef = dialogRef
    this.valueFormControl.valueChanges.subscribe(newValue => this.value = newValue)
  }

  ngOnInit(): void {
  }

  submit(): void{
    if(!this.valueFormControl.hasError('required')) {
      this.dialogRef.close(this.value)
    }
  }

  close(): void {
    this.dialogRef.close()
  }
}
