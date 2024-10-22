import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddTraderFormComponent } from './add-trader-form.component';

describe('AddTraderFormComponent', () => {
  let component: AddTraderFormComponent;
  let fixture: ComponentFixture<AddTraderFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddTraderFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddTraderFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
