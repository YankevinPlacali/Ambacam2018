import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPaysComponent } from './admin-pays.component';

describe('AdminPaysComponent', () => {
  let component: AdminPaysComponent;
  let fixture: ComponentFixture<AdminPaysComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminPaysComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPaysComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
