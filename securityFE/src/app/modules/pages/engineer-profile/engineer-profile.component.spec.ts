import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EngineerProfileComponent } from './engineer-profile.component';

describe('EngineerProfileComponent', () => {
  let component: EngineerProfileComponent;
  let fixture: ComponentFixture<EngineerProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EngineerProfileComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EngineerProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
