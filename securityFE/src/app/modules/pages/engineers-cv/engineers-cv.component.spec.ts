import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EngineersCvComponent } from './engineers-cv.component';

describe('EngineersCvComponent', () => {
  let component: EngineersCvComponent;
  let fixture: ComponentFixture<EngineersCvComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EngineersCvComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EngineersCvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
