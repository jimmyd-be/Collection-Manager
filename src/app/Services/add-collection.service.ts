import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { FormGroup, FormBuilder, FormArray, FormControl } from '@angular/forms';
import { CollectionForm } from '../Entities/collection-form';
import { CustomField } from '../Entities/custom-field';

@Injectable({
  providedIn: 'root'
})
export class AddCollectionService {

  private collectionFormPrivate: BehaviorSubject<
  FormGroup | undefined
> = new BehaviorSubject(
 this.fb.group(new CollectionForm()
));

collectionForm: Observable<FormGroup> = this.collectionFormPrivate.asObservable()

constructor(private fb: FormBuilder) {}

addCustomField() {
  const currentForm = this.collectionFormPrivate.getValue()
  const currentFields = currentForm.get('fields') as FormArray

  currentFields.push(
    this.fb.group(
      new CollectionForm()
    )
  )

  this.collectionFormPrivate.next(currentForm)
}

addCustomFieldByField(field: CustomField): void{
  const currentForm = this.collectionFormPrivate.getValue()
  const currentFields = currentForm.get('fields') as FormArray

  let form = new CollectionForm();

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
      form
    )
  )

  this.collectionFormPrivate.next(currentForm)
}

deleteCustomField(i: number) {
  const currentForm = this.collectionFormPrivate.getValue()
  const currentFields = currentForm.get('fields') as FormArray

  currentFields.removeAt(i)

  this.collectionFormPrivate.next(currentForm)
}
}
