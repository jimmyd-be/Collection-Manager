import {Component, Input, OnInit} from '@angular/core';
import {UntypedFormControl, UntypedFormGroup} from '@angular/forms';
import {CollectionShare} from '../../Entities/collectionShare';
import {CollectionService} from '../../Services/collection.service';
import {Role} from '../../Entities/Role';
import {RoleService} from '../../Services/role.service';
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";

@Component({
  selector: 'app-share-collection-dialog',
  templateUrl: './share-collection-dialog.component.html',
  styleUrls: ['./share-collection-dialog.component.scss'],
})
export class ShareCollectionDialogComponent implements OnInit {

  @Input() public collectionId: number;
  roles: Role[];
  public form: UntypedFormGroup = new UntypedFormGroup({
    userName: new UntypedFormControl(''),
    role: new UntypedFormControl(''),
  });
  private usernameLabel = 'userName';
  private roleLabel = 'role';

  constructor(private collectionService: CollectionService, private roleService: RoleService,
              public ref: DynamicDialogRef, public config: DynamicDialogConfig) {
  }

  ngOnInit() {
    this.roleService.getActiveRoles().subscribe(data => this.roles = data);
  }

  close() {
    this.ref.close();
  }

  share() {
    const shareDto = new CollectionShare(this.form.controls[this.usernameLabel].value, this.form.controls[this.roleLabel].value);

    this.collectionService.shareCollection(shareDto, this.collectionId).subscribe(data => this.close());
  }

}
