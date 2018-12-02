import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRecepisseComponent } from './admin-recepisse.component';

describe('AdminRecepisseComponent', () => {
  let component: AdminRecepisseComponent;
  let fixture: ComponentFixture<AdminRecepisseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminRecepisseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminRecepisseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
