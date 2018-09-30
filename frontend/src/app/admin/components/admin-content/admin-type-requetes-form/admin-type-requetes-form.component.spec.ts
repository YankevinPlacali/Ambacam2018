import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminTypeRequetesFormComponent } from './admin-type-requetes-form.component';

describe('AdminTypeRequetesFormComponent', () => {
  let component: AdminTypeRequetesFormComponent;
  let fixture: ComponentFixture<AdminTypeRequetesFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminTypeRequetesFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminTypeRequetesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
