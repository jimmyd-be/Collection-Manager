import { TestBed } from '@angular/core/testing';

import { CustomFieldService } from './custom-field.service';

describe('CustomFieldService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CustomFieldService = TestBed.get(CustomFieldService);
    expect(service).toBeTruthy();
  });
});
