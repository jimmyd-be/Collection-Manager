import { TestBed } from '@angular/core/testing';

import { AddCollectionService } from './add-collection.service';

describe('AddCollectionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AddCollectionService = TestBed.get(AddCollectionService);
    expect(service).toBeTruthy();
  });
});
