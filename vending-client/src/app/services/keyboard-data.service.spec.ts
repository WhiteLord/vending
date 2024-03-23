import { TestBed } from '@angular/core/testing';

import { KeyboardDataService } from './keyboard-data.service';

describe('KeyboardDataService', () => {
  let service: KeyboardDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KeyboardDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
