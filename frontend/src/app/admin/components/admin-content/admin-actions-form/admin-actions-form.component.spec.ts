import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminActionsFormComponent } from './admin-actions-form.component';

describe('AdminActionsFormComponent', () => {
  let component: AdminActionsFormComponent;
  let fixture: ComponentFixture<AdminActionsFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminActionsFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminActionsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
