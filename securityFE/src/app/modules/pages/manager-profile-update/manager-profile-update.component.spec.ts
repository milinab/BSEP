import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerProfileUpdateComponent } from './manager-profile-update.component';

describe('ManagerProfileUpdateComponent', () => {
  let component: ManagerProfileUpdateComponent;
  let fixture: ComponentFixture<ManagerProfileUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagerProfileUpdateComponent ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ManagerProfileUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
