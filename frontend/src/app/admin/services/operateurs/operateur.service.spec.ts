import { TestBed, inject } from '@angular/core/testing';

import { OperateurService } from './operateur.service';

describe('OperateurService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [OperateurService]
    });
  });

  it('should be created', inject([OperateurService], (service: OperateurService) => {
    expect(service).toBeTruthy();
  }));
});
