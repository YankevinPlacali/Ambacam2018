import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminOperateursComponent } from './admin-operateurs.component';

describe('AdminOperateursComponent', () => {
  let component: AdminOperateursComponent;
  let fixture: ComponentFixture<AdminOperateursComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminOperateursComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminOperateursComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
