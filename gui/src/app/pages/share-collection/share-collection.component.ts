import { Component, OnInit } from '@angular/core';
import { Collection } from '../../Entities/collection';
import { CollectionService } from '../../Services/collection.service';
import { Role } from '../../Entities/Role';
import { RoleService } from '../../Services/role.service';
import { ActivatedRoute } from '@angular/router';
import { NbDialogService } from '@nebular/theme';
import { ShareCollectionDialogComponent } from '../share-collection-dialog/share-collection-dialog.component';
import { UserCollection } from '../../Entities/UserCollection';
import { typeWithParameters } from '@angular/compiler/src/render3/util';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'ngx-share-collection',
  templateUrl: './share-collection.component.html',
  styleUrls: ['./share-collection.component.scss'],
})
export class ShareCollectionComponent implements OnInit {

  removeIcon = faTrash;
  roleList: Role[];
  collectionId: number;
  public currentCollection: Collection;
  userCollections: UserCollection[];

  constructor(private collectionService: CollectionService, private roleService: RoleService,
    private route: ActivatedRoute, private dialogService: NbDialogService) { }

  ngOnInit() {

    this.collectionId = Number(this.route.snapshot.paramMap.get('id'));

    this.collectionService.getUserCollection(Number(this.route.snapshot.paramMap.get('id')))
    .subscribe(data => this.currentCollection = data);

    this.roleService.getActiveRoles().subscribe(data => this.roleList = data);

    this.collectionService.getUsers(this.collectionId).subscribe(data => this.userCollections = data);
  }

  openDialog() {

      this.dialogService.open(ShareCollectionDialogComponent, {context: {collectionId: this.collectionId}}).onClose.subscribe(response => {
        this.collectionService.getUsers(this.collectionId).subscribe(data => this.userCollections = data);
      });
  }

  deleteUser(userId: Number) {
    this.collectionService.deleteUserFromCollection(userId, this.collectionId).subscribe(d =>
      this.collectionService.getUsers(this.collectionId).subscribe(data => this.userCollections = data));
  }

}
