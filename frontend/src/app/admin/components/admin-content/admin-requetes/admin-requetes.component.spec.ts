import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRequetesComponent } from './admin-requetes.component';

describe('AdminRequetesComponent', () => {
  let component: AdminRequetesComponent;
  let fixture: ComponentFixture<AdminRequetesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminRequetesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminRequetesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
