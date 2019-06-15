import { Component, OnInit } from '@angular/core';
import { User } from '../../Entities/user';
import { UserService } from '../../Services/user.service';

@Component({
  selector: 'ngx-view-user',
  templateUrl: './view-user.component.html',
  styleUrls: ['./view-user.component.scss']
})
export class ViewUserComponent implements OnInit {

  user: User;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.getUser().subscribe(data => {
      this.user = data;
    });
  }

}
