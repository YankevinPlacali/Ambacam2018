import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRequeteGroupesModalComponent } from './admin-requete-groupes-modal.component';

describe('AdminRequeteGroupesModalComponent', () => {
  let component: AdminRequeteGroupesModalComponent;
  let fixture: ComponentFixture<AdminRequeteGroupesModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminRequeteGroupesModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminRequeteGroupesModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
