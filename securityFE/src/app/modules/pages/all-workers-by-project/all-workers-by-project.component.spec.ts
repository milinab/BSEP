import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllWorkersByProjectComponent } from './all-workers-by-project.component';

describe('AllWorkersByProjectComponent', () => {
  let component: AllWorkersByProjectComponent;
  let fixture: ComponentFixture<AllWorkersByProjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllWorkersByProjectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllWorkersByProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
