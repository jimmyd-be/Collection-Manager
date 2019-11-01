import { Component, OnInit } from '@angular/core';
import { EditUser } from '../../Entities/EditUser';
import { UserService } from '../../Services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'ngx-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss'],
})
export class EditUserComponent implements OnInit {

  constructor(private userService: UserService, private router: Router) { }

  model: EditUser = new EditUser('', '', '');

  ngOnInit() {
    this.userService.getUser().subscribe(data => {
        this.model = new EditUser('', data.username, data.mail);
    });
  }

  onSubmit () {

    this.userService.editUser(this.model).subscribe(data => {
      this.router.navigate(['/pages/profile']);
    },
    error => {
      this.model.password = '';
    });

  }

}
