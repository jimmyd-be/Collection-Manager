import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchItemResultComponent } from './search-item-result.component';

describe('SearchItemResultComponent', () => {
  let component: SearchItemResultComponent;
  let fixture: ComponentFixture<SearchItemResultComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchItemResultComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchItemResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
