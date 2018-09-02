import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminOperateursFormComponent } from './admin-operateurs-form.component';

describe('AdminOperateursFormComponent', () => {
  let component: AdminOperateursFormComponent;
  let fixture: ComponentFixture<AdminOperateursFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminOperateursFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminOperateursFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
