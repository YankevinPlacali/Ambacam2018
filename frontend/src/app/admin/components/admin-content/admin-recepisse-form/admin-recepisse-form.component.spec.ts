import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRecepisseFormComponent } from './admin-recepisse-form.component';

describe('AdminRecepisseFormComponent', () => {
  let component: AdminRecepisseFormComponent;
  let fixture: ComponentFixture<AdminRecepisseFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminRecepisseFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminRecepisseFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
