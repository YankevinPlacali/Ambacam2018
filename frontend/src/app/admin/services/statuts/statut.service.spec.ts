import { TestBed, inject } from '@angular/core/testing';

import { StatutService } from './statut.service';

describe('StatutService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StatutService]
    });
  });

  it('should be created', inject([StatutService], (service: StatutService) => {
    expect(service).toBeTruthy();
  }));
});
