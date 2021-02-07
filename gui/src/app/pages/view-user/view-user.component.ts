import {Component, OnInit} from '@angular/core';
import {User} from '../../Entities/user';
import {UserService} from '../../Services/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-view-user',
  templateUrl: './view-user.component.html',
  styleUrls: ['./view-user.component.scss'],
})
export class ViewUserComponent implements OnInit {

  user: User;

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit() {
    this.userService.getUser().subscribe(data => {
      this.user = data;
    });
  }

  openpage(page: string) {

    if (page === 'edit') {
      this.router.navigate(['/pages/profile/edit']);
    } else if (page === 'delete') {
      this.router.navigate(['/pages/profile/delete']);
    } else if (page === 'changePassword') {
      this.router.navigate(['/pages/profile/edit/password']);
    }
  }

}
