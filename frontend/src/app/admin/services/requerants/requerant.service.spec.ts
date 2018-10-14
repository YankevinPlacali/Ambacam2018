import { TestBed, inject } from '@angular/core/testing';

import { RequerantService } from './requerant.service';

describe('RequerantService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RequerantService]
    });
  });

  it('should be created', inject([RequerantService], (service: RequerantService) => {
    expect(service).toBeTruthy();
  }));
});
