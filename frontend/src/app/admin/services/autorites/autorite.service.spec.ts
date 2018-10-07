import { TestBed, inject } from '@angular/core/testing';

import { AutoriteService } from './autorite.service';

describe('AutoriteService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AutoriteService]
    });
  });

  it('should be created', inject([AutoriteService], (service: AutoriteService) => {
    expect(service).toBeTruthy();
  }));
});
