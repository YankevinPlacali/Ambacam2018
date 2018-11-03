import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRequetesFormComponent } from './admin-requetes-form.component';

describe('AdminRequetesFormComponent', () => {
  let component: AdminRequetesFormComponent;
  let fixture: ComponentFixture<AdminRequetesFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminRequetesFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminRequetesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
