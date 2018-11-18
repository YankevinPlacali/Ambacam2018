import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPassportsFormComponent } from './admin-passports-form.component';

describe('AdminPassportsFormComponent', () => {
  let component: AdminPassportsFormComponent;
  let fixture: ComponentFixture<AdminPassportsFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminPassportsFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPassportsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
