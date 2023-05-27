import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerCurrentProjectsComponent } from './manager-current-projects.component';

describe('ManagerCurrentProjectsComponent', () => {
  let component: ManagerCurrentProjectsComponent;
  let fixture: ComponentFixture<ManagerCurrentProjectsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagerCurrentProjectsComponent ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ManagerCurrentProjectsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
