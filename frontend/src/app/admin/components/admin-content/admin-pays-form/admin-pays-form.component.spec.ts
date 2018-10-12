import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPaysFormComponent } from './admin-pays-form.component';

describe('AdminPaysFormComponent', () => {
  let component: AdminPaysFormComponent;
  let fixture: ComponentFixture<AdminPaysFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminPaysFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPaysFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
