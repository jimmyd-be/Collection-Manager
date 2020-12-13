import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PasswordUser } from '../../Entities/PasswordUser';
import { UserService } from '../../Services/user.service';

@Component({
  selector: 'app-edit-password-user',
  templateUrl: './edit-password-user.component.html',
  styleUrls: ['./edit-password-user.component.scss'],
})
export class EditPasswordUserComponent implements OnInit {

  model: PasswordUser = new PasswordUser('', '', '');

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
  }


  onSubmit() {

    this.userService.editPassword(this.model).subscribe(data => {
      this.router.navigate(['/pages/profile']);
    },
    error => {
      this.model.password = '';
      this.model.currentPassword = '';
      this.model.passwordRepeat = '';
    });

  }


}
