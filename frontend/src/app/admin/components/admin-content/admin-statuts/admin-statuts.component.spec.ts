import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminStatutsComponent } from './admin-statuts.component';

describe('AdminStatutsComponent', () => {
  let component: AdminStatutsComponent;
  let fixture: ComponentFixture<AdminStatutsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminStatutsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminStatutsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
