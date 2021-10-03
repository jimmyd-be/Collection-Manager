import {Component} from '@angular/core';
import {UserService} from '../../Services/user.service';
import {Router} from '@angular/router';
import {DeleteUser} from '../../Entities/DeleteUser';

@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrls: ['./delete-user.component.scss'],
})
export class DeleteUserComponent {

  model: DeleteUser = new DeleteUser('', '');

  constructor(private userService: UserService, private router: Router) {
  }

  onSubmit() {

    this.userService.deleteUser(this.model).subscribe(data => {
        this.router.navigate(['/auth/logout']);
      },
      error => {
        this.model.password = '';
      });

  }

}
