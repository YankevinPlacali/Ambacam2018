import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAutoritesComponent } from './admin-autorites.component';

describe('AdminAutoritesComponent', () => {
  let component: AdminAutoritesComponent;
  let fixture: ComponentFixture<AdminAutoritesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminAutoritesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminAutoritesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
