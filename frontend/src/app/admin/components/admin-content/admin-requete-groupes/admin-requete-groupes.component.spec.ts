import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRequeteGroupesComponent } from './admin-requete-groupes.component';

describe('AdminRequeteGroupesComponent', () => {
  let component: AdminRequeteGroupesComponent;
  let fixture: ComponentFixture<AdminRequeteGroupesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminRequeteGroupesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminRequeteGroupesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
