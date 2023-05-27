import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerPastProjectsComponent } from './manager-past-projects.component';

describe('ManagerPastProjectsComponent', () => {
  let component: ManagerPastProjectsComponent;
  let fixture: ComponentFixture<ManagerPastProjectsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagerPastProjectsComponent ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ManagerPastProjectsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
