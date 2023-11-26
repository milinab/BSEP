import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchEngineersComponent } from './search-engineers.component';

describe('SearchEngineersComponent', () => {
  let component: SearchEngineersComponent;
  let fixture: ComponentFixture<SearchEngineersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchEngineersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchEngineersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
