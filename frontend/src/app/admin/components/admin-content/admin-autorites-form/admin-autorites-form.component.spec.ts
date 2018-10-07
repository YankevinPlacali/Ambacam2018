import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAutoritesFormComponent } from './admin-autorites-form.component';

describe('AdminAutoritesFormComponent', () => {
  let component: AdminAutoritesFormComponent;
  let fixture: ComponentFixture<AdminAutoritesFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminAutoritesFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminAutoritesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
