import { TestBed, inject } from '@angular/core/testing';

import { RecepisseService } from './recepisse.service';

describe('RecepisseService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RecepisseService]
    });
  });

  it('should be created', inject([RecepisseService], (service: RecepisseService) => {
    expect(service).toBeTruthy();
  }));
});
