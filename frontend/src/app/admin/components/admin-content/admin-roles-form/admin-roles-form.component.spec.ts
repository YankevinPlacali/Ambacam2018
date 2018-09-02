import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRolesFormComponent } from './admin-roles-form.component';

describe('AdminRolesFormComponent', () => {
  let component: AdminRolesFormComponent;
  let fixture: ComponentFixture<AdminRolesFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminRolesFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminRolesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
