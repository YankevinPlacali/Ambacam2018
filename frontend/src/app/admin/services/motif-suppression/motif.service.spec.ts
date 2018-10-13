import { TestBed, inject } from '@angular/core/testing';

import { MotifSuppressionService } from './motif-suppression.service';

describe('MotifSuppressionService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MotifSuppressionService]
    });
  });

  it('should be created', inject([MotifSuppressionService], (service: MotifSuppressionService) => {
    expect(service).toBeTruthy();
  }));
});
