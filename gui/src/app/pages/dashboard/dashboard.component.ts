import {Component, OnInit} from '@angular/core';
import {Collection} from '../../Entities/collection';
import {CollectionService} from '../../Services/collection.service';
import {faEdit, faEye, faShareAltSquare, faTrash} from '@fortawesome/free-solid-svg-icons';
import {Router} from '@angular/router';
import {NbDialogService} from '@nebular/theme';
import {ConfirmationDialogComponent} from '../confirmation-dialog/confirmation-dialog.component';
import {NbAuthJWTToken, NbAuthService} from '@nebular/auth';
import {MessageService} from "primeng/api";


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  providers: [MessageService],
})
export class DashboardComponent implements OnInit {

  removeIcon = faTrash;
  editIcon = faEdit;
  viewIcon = faEye;
  shareIcon = faShareAltSquare;

  collections: Collection[];

  constructor(private collectionService: CollectionService,
              private router: Router,
              private dialogService: NbDialogService,
              private authService: NbAuthService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.loadCollections();
  }

  loadCollections() {

    this.authService.onTokenChange().subscribe((token: NbAuthJWTToken) => {
      this.collectionService.getUserCollections().subscribe(data => {

        this.collections = data;
      });
    });
  }

  editCollection(id: number) {
    this.router.navigate(['/pages/collection/edit', id]);
  }

  deleteCollection(id: number, name: string) {

    this.dialogService.open(ConfirmationDialogComponent)
      .onClose.subscribe(response => {
        if (response === 'delete') {

          this.collectionService.deleteCollection(id).subscribe(data => {
            location.reload();

            this.messageService.add({severity:'success', summary:'Collection ' + name + ' has been deleted.'});

            this.router.navigate(['/pages/dashboard']);

            this.loadCollections();
          });
        }
      },
      error =>
        this.messageService.add({severity:'error', summary:'Collection ' + name + ' has nog been delete because of an error!'}));
  }

  viewCollection(id: number) {
    this.router.navigate(['/pages/collection/view', id]);
  }

  shareCollection(id: number) {
    this.router.navigate(['/pages/collection/share', id]);
  }
}
