import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminTypeRequetesComponent } from './admin-type-requetes.component';

describe('AdminTypeRequetesComponent', () => {
  let component: AdminTypeRequetesComponent;
  let fixture: ComponentFixture<AdminTypeRequetesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminTypeRequetesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminTypeRequetesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
