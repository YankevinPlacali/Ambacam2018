import { TestBed, inject } from '@angular/core/testing';

import { RqueteGroupeService } from './requete-groupe.service';

describe('RequeteGroupeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RqueteGroupeService]
    });
  });

  it('should be created', inject([RqueteGroupeService], (service: RqueteGroupeService) => {
    expect(service).toBeTruthy();
  }));
});
