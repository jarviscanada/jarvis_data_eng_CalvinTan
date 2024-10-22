import { TestBed } from '@angular/core/testing';

import { TraderListService } from './trader-list.service';

describe('TraderListService', () => {
  let service: TraderListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TraderListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
