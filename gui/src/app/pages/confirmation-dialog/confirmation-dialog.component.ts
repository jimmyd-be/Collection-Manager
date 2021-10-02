import {Component} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.scss'],
})
export class ConfirmationDialogComponent {

  data: string;

  constructor(private dialogRef: NbDialogRef<string>) {
  }

  delete() {

    this.dialogRef.close('delete');
  }

  cancel() {
    this.dialogRef.close('cancel');
  }
}
