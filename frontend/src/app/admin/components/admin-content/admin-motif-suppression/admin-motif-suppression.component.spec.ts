import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminMotifSuppressionComponent } from './admin-motif-suppression.component';

describe('AdminMotifSuppressionComponent', () => {
  let component: AdminMotifSuppressionComponent;
  let fixture: ComponentFixture<AdminMotifSuppressionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminMotifSuppressionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminMotifSuppressionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
