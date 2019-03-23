import { TestBed } from '@angular/core/testing';

import { ManualFormService } from './manual-form.service';

describe('ManualFormService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ManualFormService = TestBed.get(ManualFormService);
    expect(service).toBeTruthy();
  });
});
