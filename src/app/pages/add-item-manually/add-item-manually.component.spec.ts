import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddItemManuallyComponent } from './add-item-manually.component';

describe('AddItemManuallyComponent', () => {
  let component: AddItemManuallyComponent;
  let fixture: ComponentFixture<AddItemManuallyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddItemManuallyComponent ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddItemManuallyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
