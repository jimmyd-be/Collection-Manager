import {Component, OnInit} from '@angular/core';
import {User} from '../../Entities/user';
import {AdminService} from '../../Services/admin.service';
import {faEdit, faTrash} from '@fortawesome/free-solid-svg-icons';
import {UserService} from '../../Services/user.service';
import {NbDialogService} from '@nebular/theme';
import {ConfirmationDialogComponent} from '../confirmation-dialog/confirmation-dialog.component';
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.scss'],
  providers: [MessageService]
})
export class AdminUsersComponent implements OnInit {

  editIcon = faEdit;
  removeIcon = faTrash;
  users: User[];

  constructor(public adminService: AdminService,
              public userService: UserService,
              private dialogService: NbDialogService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.users = null;
    this.adminService.getAllUsers().subscribe(list => this.users = list);
  }

  disableUser(userId: number): void {

    const user = this.users.find(element => element.id === userId);

    if (!user.active) {
      this.userService.enableUser(userId).subscribe(
        response => user.active = true,
        error => user.active = false
      );
    } else {
      this.userService.disableUser(userId).subscribe(
        response => user.active = false,
        error => user.active = true
      );
    }
  }

  setAdmin(userId: number) {
    const user = this.users.find(element => element.id === userId);

    this.userService.setAdmin(userId, !user.admin).subscribe(
      response => user.admin = !user.admin,
      error => user.admin = user.admin
    );

  }

  deleteUser(userId: number) {
    const user = this.users.find(element => element.id === userId);

    this.dialogService.open(ConfirmationDialogComponent)
      .onClose.subscribe(response => {
        if (response === 'delete') {

          this.userService.deleteUserOnId(userId).subscribe(
            resp => {
              const index: number = this.users.indexOf(user);
              if (index !== -1) {
                this.users.splice(index, 1);
              }
            }
          );
        }
      },
      error => this.messageService.add({severity:'succes', summary:'User has been delete by admin!'}));
  }
}
