import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UntypedFormGroup, UntypedFormBuilder, UntypedFormArray } from '@angular/forms';
import { CollectionForm } from '../Entities/collection-form';
import { CustomField } from '../Entities/custom-field';

@Injectable({
  providedIn: 'root',
})
export class AddCollectionService {

  private collectionFormPrivate: BehaviorSubject<
  UntypedFormGroup | undefined
> = new BehaviorSubject(
 this.fb.group(new CollectionForm(),
));

collectionForm: Observable<UntypedFormGroup> = this.collectionFormPrivate.asObservable();

constructor(private fb: UntypedFormBuilder) {}

addCustomField() {
  const currentForm = this.collectionFormPrivate.getValue();
  const currentFields = currentForm.get('fields') as UntypedFormArray;

  currentFields.push(
    this.fb.group(
      new CollectionForm(),
    ),
  );

  this.collectionFormPrivate.next(currentForm);
}

addCustomFieldByField(field: CustomField): void {
  const currentForm = this.collectionFormPrivate.getValue();
  const currentFields = currentForm.get('fields') as UntypedFormArray;

  const form = new CollectionForm();

  form.id.setValue(field.id);
  form.name.setValue(field.name);
  form.type.setValue(field.type);
  form.choises.setValue(field.options);
  form.placeholder.setValue(field.placeholder);
  form.required.setValue(field.required);
  form.multiValue.setValue(field.multivalues);
  form.fieldOrder.setValue(field.fieldOrder);
  form.place.setValue(field.place);
  form.labelposition.setValue(field.labelPosition);
  form.label.setValue(field.label);

  currentFields.push(
    this.fb.group(
      form,
    ),
  );

  this.collectionFormPrivate.next(currentForm);
}

deleteCustomField(i: number) {
  const currentForm = this.collectionFormPrivate.getValue();
  const currentFields = currentForm.get('fields') as UntypedFormArray;

  currentFields.removeAt(i);

  this.collectionFormPrivate.next(currentForm);
}
}
