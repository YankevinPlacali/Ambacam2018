import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPassportsComponent } from './admin-passports.component';

describe('AdminPassportsComponent', () => {
  let component: AdminPassportsComponent;
  let fixture: ComponentFixture<AdminPassportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminPassportsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPassportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
