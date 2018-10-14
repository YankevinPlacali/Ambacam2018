import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRequerantsFormComponent } from './admin-requerants-form.component';

describe('AdminRequerantsFormComponent', () => {
  let component: AdminRequerantsFormComponent;
  let fixture: ComponentFixture<AdminRequerantsFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminRequerantsFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminRequerantsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
