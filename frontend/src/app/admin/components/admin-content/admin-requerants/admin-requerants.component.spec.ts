import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRequerantsComponent } from './admin-requerants.component';

describe('AdminRequerantsComponent', () => {
  let component: AdminRequerantsComponent;
  let fixture: ComponentFixture<AdminRequerantsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminRequerantsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminRequerantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
