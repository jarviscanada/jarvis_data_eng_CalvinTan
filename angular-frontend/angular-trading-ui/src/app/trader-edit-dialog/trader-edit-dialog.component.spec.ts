import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TraderEditDialogComponent } from './trader-edit-dialog.component';

describe('TraderEditDialogComponent', () => {
  let component: TraderEditDialogComponent;
  let fixture: ComponentFixture<TraderEditDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TraderEditDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TraderEditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
