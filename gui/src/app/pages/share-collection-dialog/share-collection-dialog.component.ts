import {Component, Input, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {FormControl, FormGroup} from '@angular/forms';
import {CollectionShare} from '../../Entities/collectionShare';
import {CollectionService} from '../../Services/collection.service';
import {Role} from '../../Entities/Role';
import {RoleService} from '../../Services/role.service';

@Component({
  selector: 'app-share-collection-dialog',
  templateUrl: './share-collection-dialog.component.html',
  styleUrls: ['./share-collection-dialog.component.scss'],
})
export class ShareCollectionDialogComponent implements OnInit {

  @Input() public collectionId: number;
  roles: Role[];
  public form: FormGroup = new FormGroup({
    userName: new FormControl(''),
    role: new FormControl(''),
  });
  private usernameLabel = 'userName';
  private roleLabel = 'role';

  constructor(private dialogRef: NbDialogRef<number>, private collectionService: CollectionService, private roleService: RoleService) {
  }

  ngOnInit() {
    this.roleService.getActiveRoles().subscribe(data => this.roles = data);
  }

  close() {
    this.dialogRef.close();
  }

  share() {
    const shareDto = new CollectionShare(this.form.controls[this.usernameLabel].value, this.form.controls[this.roleLabel].value);

    this.collectionService.shareCollection(shareDto, this.collectionId).subscribe(data => this.close());
  }

}
