import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminMotifSuppressionFormComponent} from './admin-motif-suppression-form.component';

describe('AdminMotifSuppressionFormComponent', () => {
  let component: AdminMotifSuppressionFormComponent;
  let fixture: ComponentFixture<AdminMotifSuppressionFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminMotifSuppressionFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminMotifSuppressionFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
