import { TestBed, inject } from '@angular/core/testing';

import { TypeRequeteService } from './type-requete.service';

describe('TypeRequeteService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TypeRequeteService]
    });
  });

  it('should be created', inject([TypeRequeteService], (service: TypeRequeteService) => {
    expect(service).toBeTruthy();
  }));
});
