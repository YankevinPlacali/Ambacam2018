import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminStatutsFormComponent } from './admin-statuts-form.component';

describe('AdminStatutsFormComponent', () => {
  let component: AdminStatutsFormComponent;
  let fixture: ComponentFixture<AdminStatutsFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminStatutsFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminStatutsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
