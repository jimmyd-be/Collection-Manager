import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { FormGroup, FormBuilder, FormArray, FormControl } from '@angular/forms';
import { CollectionForm } from '../Entities/collection-form';
import { CustomField } from '../Entities/custom-field';

@Injectable({
  providedIn: 'root'
})
export class AddCollectionService {

  private teamForm: BehaviorSubject<
  FormGroup | undefined
> = new BehaviorSubject(
 this.fb.group(new CollectionForm()
))
teamForm$: Observable<FormGroup> = this.teamForm.asObservable()

constructor(private fb: FormBuilder) {}

addCustomField() {
  const currentTeam = this.teamForm.getValue()
  const currentPlayers = currentTeam.get('fields') as FormArray

  currentPlayers.push(
    this.fb.group(
      new CollectionForm()
    )
  )

  this.teamForm.next(currentTeam)
}

deleteCustomField(i: number) {
  const currentTeam = this.teamForm.getValue()
  const currentPlayers = currentTeam.get('fields') as FormArray

  currentPlayers.removeAt(i)

  this.teamForm.next(currentTeam)
}
}
