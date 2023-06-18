import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CvEngineersComponent } from './cv-engineers.component';

describe('CvEngineersComponent', () => {
  let component: CvEngineersComponent;
  let fixture: ComponentFixture<CvEngineersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CvEngineersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CvEngineersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
